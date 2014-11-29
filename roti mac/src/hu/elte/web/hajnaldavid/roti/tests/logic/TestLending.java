package hu.elte.web.hajnaldavid.roti.tests.logic;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.BicycleDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.LendingDomain;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle.BikeType;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.BicycleBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.CustomerBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.postgresql.util.PSQLException;

public class TestLending {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private CustomerBuilder customerBuilder;
	@SuppressWarnings("unused")
	private StationBuilder stationBuilder;
	private BicycleBuilder bicylceBuilder;
	private static StationDomain stationDomain;
	private static LendingDomain lendingDomain;
	private static Station station;
	private static List<Long> lendingIds = new ArrayList();

	public TestLending() {
		customerBuilder = new CustomerBuilder();
		stationBuilder = new StationBuilder();
		bicylceBuilder = new BicycleBuilder();
	}

	@Before
	public void init() {
		log4j.debug("before");
		stationDomain = new StationDomain();

		lendingDomain = new LendingDomain();

		if (station == null)
			station = new StationBuilder().setName("[Teszt] kölcsönzés")
					.setMaximumCapacity(10).getInstance();

		station = stationDomain.update(station);
	}

	@After
	public void cleanBikes() {
		log4j.debug("cleanBikes");
		BicycleDomain bicycleDomain = new BicycleDomain();
		for (Bicycle b : bicycleDomain.readAll()) {
			try {
				bicycleDomain.delete(b);
			} catch (Exception ex) {
				//nem minden teszt eset után lehet törölni
				//hacky de nem akartam külön osztályba szervezni ezeket a teszteket
				//log4j.debug(ex.getMessage());
			}
		}
	}

	@AfterClass
	public static void clean() {

		log4j.debug("after");

		for (Long id : lendingIds) {
			Integer currentId = id.intValue();
			Lending lending = lendingDomain.read(currentId);
			lendingDomain.delete(lending);
			log4j.debug("lending id: " + id);
		}

		stationDomain.update(station);

		stationDomain.delete(station);
	}

	@Test(expected = NonPayAbilityException.class)
	public void testNonPayAbilityException() throws NonPayAbilityException,
			EmptyStationException, NoSuchElement, FullCapacityException {

		log4j.debug("testNonPayAbilityException");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		bike = new BicycleDomain().update(bike);

		Customer customer = customerBuilder.setCredit(0).setName("John Doe")
				.getInstance();

		// station = stationDomain.addBike(station, bike);

		station.addBike(bike);

		Lending lending = lendingDomain.lendBicycle(customer, station, bike);

		lendingIds.add(lending.getId());

	}

	@Test(expected = EmptyStationException.class)
	public void testEmptyStationException() throws NonPayAbilityException,
			EmptyStationException, NoSuchElement, FullCapacityException {

		log4j.debug("testEmptyStationException");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		bike = new BicycleDomain().update(bike);

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		station = stationDomain.clearStation(station);

		Lending lending = lendingDomain.lendBicycle(customer, station, bike);

		lendingIds.add(lending.getId());

	}

	@Test()
	public void testLending() {

		log4j.debug("testLending");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		bike = new BicycleDomain().update(bike);

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		station = stationDomain.clearStation(station);

		try {
			station = stationDomain.addBike(station, bike);
			log4j.debug("bike was added to station!");
			// stationDomain.update(station);
		} catch (FullCapacityException e1) {
			log4j.debug(e1.getMessage());

		}

		int startSize = lendingDomain.readAll().size();

		try {

			Lending lending = lendingDomain
					.lendRandomBicycle(customer, station);

			lendingIds.add(lending.getId());

			log4j.debug(lendingDomain.readAll().size());

			log4j.debug("lending was created!");

		} catch (NonPayAbilityException e) {
			log4j.debug(e.getMessage());
		} catch (EmptyStationException e) {
			log4j.debug(e.getMessage());
		} catch (NoSuchElement e) {
			log4j.debug(e.getMessage());
		}

		int currentSize = lendingDomain.readAll().size();

		Assert.assertNotEquals(startSize, currentSize);
		Assert.assertEquals(0, station.getBikes().size());
		Assert.assertEquals(0, customer.getCredit().intValue());

	}

	@Test()
	public void testFindByCustomer() throws FullCapacityException {

		log4j.debug("testFindByCustomer");

		Bicycle bike = bicylceBuilder.setLendingPrice(1)
				.setType(BikeType.CAMPING).getInstance();

		bike = new BicycleDomain().update(bike);

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		customer = new GenericDao<Customer>(Customer.class).update(customer);

		station = stationDomain.clearStation(station);

		station = stationDomain.addBike(station, bike);

		try {

			log4j.debug("create lending");
			log4j.debug(customer);
			log4j.debug(station);
			log4j.debug(bike);

			Lending lending = lendingDomain
					.lendBicycle(customer, station, bike);

			lendingIds.add(lending.getId());

			log4j.debug(lendingDomain.readAll().size());

			log4j.debug("lending was created!");

			log4j.debug(customer);
			log4j.debug(station);
			log4j.debug(bike);

		} catch (NonPayAbilityException e) {
			log4j.debug(e.getMessage());
		} catch (EmptyStationException e) {
			log4j.debug(e.getMessage());
		} catch (NoSuchElement e) {
			log4j.debug(e.getMessage());
		}

		Bicycle b = lendingDomain.getBicycleByCustomer(customer);

		Assert.assertEquals(bike, b);

	}

	@Test
	public void testReturn() throws FullCapacityException {

		log4j.debug("testReturn");

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		bike = new BicycleDomain().update(bike);

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		station = stationDomain.clearStation(station);

		station = stationDomain.addBike(station, bike);

		int startSize = lendingDomain.readAll().size();

		try {

			Lending lending = lendingDomain
					.lendBicycle(customer, station, bike);

			log4j.debug("lending was created!");

			lendingIds.add(lending.getId());

			station = stationDomain.returnBicycle(customer, station);

		} catch (NonPayAbilityException e) {
			log4j.debug(e.getMessage());
		} catch (EmptyStationException e) {
			log4j.debug(e.getMessage());
		} catch (NoSuchElement e) {
			log4j.debug(e.getMessage());
		} catch (FullCapacityException e) {
			log4j.debug(e.getMessage());
		}

		int currentSize = lendingDomain.readAll().size();

		Assert.assertNotEquals(startSize, currentSize);
		Assert.assertEquals(1, station.getBikes().size());
		Assert.assertEquals(0, customer.getCredit().intValue());

	}

}
