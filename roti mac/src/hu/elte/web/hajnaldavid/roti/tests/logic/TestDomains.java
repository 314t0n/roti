package hu.elte.web.hajnaldavid.roti.tests.logic;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain.Status;
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

	private StationDomain<Station> stationDomain;

	private Station station;

	@Before
	public void init() {
		stationDomain = new StationDomain<Station>(Station.class);

		station = new StationBuilder().setName("Teszt").setMaximumCapacity(2)
				.getInstance();
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

		stationDomain.create(station);

		Assert.assertEquals(size, station.getBikes().size());

		stationDomain.addBike(station, testBike1);

		Assert.assertEquals(++size, station.getBikes().size());

		stationDomain.addBike(station, testBike2);

		Assert.assertEquals(++size, station.getBikes().size());

		stationDomain.addBike(station, testBike3);
		// shouldn't reach this
		Assert.assertNotEquals(++size, station.getBikes().size());

	}

	@After()
	public void clean() {
		stationDomain.delete(station);
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
}
