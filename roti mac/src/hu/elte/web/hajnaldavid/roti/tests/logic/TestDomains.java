package hu.elte.web.hajnaldavid.roti.tests.logic;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.BicycleDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain.Status;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.BicycleBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestDomains {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private StationDomain stationDomain;

	private Station station;
	private Station stationTransferTo;

	@Before
	public void init() {
		stationDomain = new StationDomain();

		try {

			Station test = stationDomain
					.findByName("[Test] Árvíztûrõ tükörfúrógép");

			stationDomain.delete(test);

		} catch (Exception ex) {
		}

		station = new StationBuilder().setName("[Test] Árvíztûrõ tükörfúrógép")
				.setMaximumCapacity(2).getInstance();

		station = stationDomain.create(station);

		stationTransferTo = new StationBuilder().setName("[Teszt] Szállítás")
				.setMaximumCapacity(1).getInstance();
		
		stationTransferTo = stationDomain.create(stationTransferTo);
	}

	@Test(expected = FullCapacityException.class)
	public void testAddBike() throws FullCapacityException {

		int size = 0;

		Bicycle testBike1 = new BicycleBuilder().setLendingPrice(999)
				.getInstance();
		Bicycle testBike2 = new BicycleBuilder().setLendingPrice(999)
				.getInstance();
		Bicycle testBike3 = new BicycleBuilder().setLendingPrice(999)
				.getInstance();

		Assert.assertEquals(size, station.getBikes().size());

		station = stationDomain.addBike(station, testBike1);
		
		station = stationDomain.update(station);

		Assert.assertEquals(++size, station.getBikes().size());

		station = stationDomain.addBike(station, testBike2);
		
		station = stationDomain.update(station);

		Assert.assertEquals(++size, station.getBikes().size());

		station = stationDomain.addBike(station, testBike3);
		
		station = stationDomain.update(station);
		
		// shouldn't reach this
		Assert.assertNotEquals(++size, station.getBikes().size());

	}

	@After()
	public void clean() {	
		stationDomain.delete(station);
		stationDomain.delete(stationTransferTo);
	}

	@Test
	public void findByName() {

		station.setName("Árvíztûrõ tükörfúrógép");

		stationDomain.update(station);

		Station stationSearch = stationDomain
				.findByName("Árvíztûrõ tükörfúrógép");

		Assert.assertEquals(station.getName(), stationSearch.getName());

		stationDomain.delete(stationSearch);

	}

	@Test
	public void warningStatus() throws NoSuchElement {

		int size = station.getBikes().size();

		while (size > 0) {
			station.removeBikeByIndex(size--);
		}

		Status status = stationDomain.getWarningLevelStatus(station);

		Assert.assertEquals(Status.ERROR.getValue(), status.getValue());

		station.addBike(new Bicycle());

		status = stationDomain.getWarningLevelStatus(station);

		Assert.assertEquals(Status.WARNING.getValue(), status.getValue());

		station.addBike(new Bicycle());

		status = stationDomain.getWarningLevelStatus(station);

		Assert.assertEquals(Status.NORMAL.getValue(), status.getValue());

	}

	@Test
	public void testTransfer() {

		int initSize = stationTransferTo.getBikes().size();

		Bicycle testBike = new BicycleBuilder().setLendingPrice(987)
				.getInstance();

		station.addBike(testBike);

		try {
			stationDomain.transferBike(station, stationTransferTo, 1);

			station = stationDomain.update(station);
			stationTransferTo = stationDomain.update(stationTransferTo);
						
		} catch (EmptyStationException | FullCapacityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals(initSize + 1, stationTransferTo.getBikes().size());

//		Assert.assertEquals(testBike, stationTransferTo.getBikes()
//				.get(initSize));

	}

	@Test
	public void testTransferMultiple() {

		int initSize = stationTransferTo.getBikes().size();

		Bicycle testBike1 = new BicycleBuilder().setLendingPrice(999)
				.getInstance();

		Bicycle testBike2 = new BicycleBuilder().setLendingPrice(999)
				.getInstance();

		Bicycle testBike3 = new BicycleBuilder().setLendingPrice(999)
				.getInstance();

		station.addBike(testBike1);
		station.addBike(testBike2);
		station.addBike(testBike3);

		try {
			stationDomain.transferBike(station, stationTransferTo, 3);

			station = stationDomain.update(station);
			stationTransferTo = stationDomain.update(stationTransferTo);

		} catch (EmptyStationException | FullCapacityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Assert.assertEquals(initSize + 3, stationTransferTo.getBikes().size());

//		Assert.assertEquals(testBike1, stationTransferTo.getBikes().get(0));

	}
}
