package hu.elte.web.hajnaldavid.roti.tests.logic;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.LendingDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle.BikeType;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.BicycleBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.CustomerBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestLending {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private CustomerBuilder customerBuilder;
	@SuppressWarnings("unused")
	private StationBuilder stationBuilder;
	private BicycleBuilder bicylceBuilder;
	private static StationDomain stationDomain;
	private static Station station;

	public TestLending() {
		customerBuilder = new CustomerBuilder();
		stationBuilder = new StationBuilder();
		bicylceBuilder = new BicycleBuilder();
	}

	@Before
	public void init() {
		log4j.debug("before");
		stationDomain = new StationDomain();

		station = new StationBuilder().setName("Teszt").setMaximumCapacity(10)
				.getInstance();
	}

	@AfterClass
	public static void clean() {
		log4j.debug("after");
		stationDomain.delete(station);
	}

	@Test(expected = NonPayAbilityException.class)
	public void testNonPayAbilityException() throws NonPayAbilityException,
			EmptyStationException, NoSuchElement {

		log4j.debug("testNonPayAbilityException");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(0).setName("John Doe")
				.getInstance();

		station.addBike(bike);

		LendingDomain lenderController = new LendingDomain();

		lenderController.lendBicycle(customer, station, bike);

	}

	@Test(expected = EmptyStationException.class)
	public void testEmptyStationException() throws NonPayAbilityException,
			EmptyStationException, NoSuchElement {

		log4j.debug("testEmptyStationException");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		stationDomain.clearStation(station);

		LendingDomain lenderController = new LendingDomain();

		lenderController.lendBicycle(customer, station, bike);

	}

	@Test()
	public void testLending() {

		log4j.debug("testLending");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		stationDomain.clearStation(station);

		try {
			stationDomain.addBike(station, bike);
			log4j.debug("bike was added to station!");
		} catch (FullCapacityException e1) {
			log4j.debug(e1.getMessage());

		}

		LendingDomain lenderController = new LendingDomain();

		int startSize = lenderController.readAll().size();

		try {

			lenderController.lendBicycle(customer, station, bike);

			log4j.debug(lenderController.readAll().size());

			log4j.debug("lending was created!");

		} catch (NonPayAbilityException e) {
			log4j.debug(e.getMessage());
		} catch (EmptyStationException e) {
			log4j.debug(e.getMessage());
		} catch (NoSuchElement e) {
			log4j.debug(e.getMessage());
		}

		int currentSize = lenderController.readAll().size();

		Assert.assertNotEquals(startSize, currentSize);
		Assert.assertEquals(0, station.getBikes().size());
		Assert.assertEquals(0, customer.getCredit().intValue());

	}

	@Test(expected = FullCapacityException.class)
	public void testReturnBike() throws FullCapacityException {

		log4j.debug("testReturnBike");

		Bicycle bike = bicylceBuilder.getInstance();
		Bicycle returnBike = bicylceBuilder.getInstance();

		stationDomain.clearStation(station);

		station.setMaximumCapacity(1);

		LendingDomain lenderController = new LendingDomain();

		lenderController.returnBicycle(bike, station);

		Assert.assertEquals(1, station.getBikes().size());

		lenderController.returnBicycle(returnBike, station);

		Assert.assertEquals(null, returnBike.getCustomer());

	}
	
	@Test()
	public void testFindByCustomer() {

		log4j.debug("testFindByCustomer");

		Bicycle bike = bicylceBuilder.setLendingPrice(1)
				.setType(BikeType.CAMPING).getInstance();

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		stationDomain.clearStation(station);

		station.addBike(bike);

		LendingDomain lenderController = new LendingDomain();

		try {

			log4j.debug("create lending");
			log4j.debug(customer);
			log4j.debug(station);
			log4j.debug(bike);

			lenderController.lendBicycle(customer, station, bike);

			log4j.debug(lenderController.readAll().size());

			log4j.debug("lending was created!");

		} catch (NonPayAbilityException e) {
			log4j.debug(e.getMessage());
		} catch (EmptyStationException e) {
			log4j.debug(e.getMessage());
		} catch (NoSuchElement e) {
			log4j.debug(e.getMessage());
		}

		Bicycle b = lenderController.getBicycleByCustomer(customer);

		Assert.assertEquals(bike, b);

	}

	public void testReturn() {

		log4j.debug("testReturn");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		stationDomain.clearStation(station);

		station.addBike(bike);

		LendingDomain lenderController = new LendingDomain();

		int startSize = lenderController.readAll().size();

		try {

			lenderController.lendBicycle(customer, station, bike);

			log4j.debug("lending was created!");

			lenderController.returnBicycle(bike, station);

		} catch (NonPayAbilityException e) {
			log4j.debug(e.getMessage());
		} catch (EmptyStationException e) {
			log4j.debug(e.getMessage());
		} catch (NoSuchElement e) {
			log4j.debug(e.getMessage());
		} catch (FullCapacityException e) {
			log4j.debug(e.getMessage());
		}

		int currentSize = lenderController.readAll().size();

		Assert.assertNotEquals(startSize, currentSize);
		Assert.assertEquals(0, station.getBikes().size());
		Assert.assertEquals(0, customer.getCredit().intValue());

	}

}
