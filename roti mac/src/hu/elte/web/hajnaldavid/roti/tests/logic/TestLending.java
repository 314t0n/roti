package hu.elte.web.hajnaldavid.roti.tests.logic;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.controllers.LenderController;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.BicycleBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.CustomerBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.StationBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TestLending {
/*
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private CustomerBuilder customerBuilder;
	private StationBuilder stationBuilder;
	private BicycleBuilder bicylceBuilder;

	public TestLending() {
		customerBuilder = new CustomerBuilder();
		stationBuilder = new StationBuilder();
		bicylceBuilder = new BicycleBuilder();
	}

	@Test(expected = NonPayAbilityException.class)
	public void testNonPayAbilityException() throws NonPayAbilityException,
			EmptyStationException, NoSuchElement {

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(0).setName("John Doe")
				.getInstance();

		Station station = stationBuilder.addBike(bike).getInstance();

		LenderController<Lending> lenderController = new LenderController<Lending>(
				Lending.class);

		lenderController.lendBicycle(customer, station, bike);

	}

	@Test(expected = EmptyStationException.class)
	public void testEmptyStationException() throws NonPayAbilityException,
			EmptyStationException, NoSuchElement {

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		Station station = stationBuilder.getInstance();

		LenderController<Lending> lenderController = new LenderController<Lending>(
				Lending.class);

		lenderController.lendBicycle(customer, station, bike);

	}

	@Test()
	public void testLending() {

		Bicycle bike = bicylceBuilder.setLendingPrice(1).getInstance();

		Customer customer = customerBuilder.setCredit(1).setName("John Doe")
				.getInstance();

		Station station = stationBuilder.addBike(bike).getInstance();

		LenderController<Lending> lenderController = new LenderController<Lending>(
				Lending.class);

		int startSize = lenderController.readAll().size();

		try {

			lenderController.lendBicycle(customer, station, bike);

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
		
		Bicycle bike = bicylceBuilder.getInstance();
		Bicycle returnBike = bicylceBuilder.getInstance();

		Station station = stationBuilder.setMaximumCapacity(2).getInstance();
		
		LenderController<Lending> lenderController = new LenderController<Lending>(
				Lending.class);
						
		lenderController.returnBicycle(bike, station);
		
		Assert.assertEquals(1, station.getBikes().size());
		
		lenderController.returnBicycle(returnBike, station);
	
	}
*/
}
