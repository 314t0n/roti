package hu.elte.web.hajnaldavid.roti.logic.controllers;

import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

public class LenderController {


	@SuppressWarnings("rawtypes")
	private LenderController checkStationBikes(Station station)
			throws EmptyStationException {
		if (!(station.getBikes().size() > 0)) {
			throw new EmptyStationException(station);
		}
		return this;
	}
	
	@SuppressWarnings("rawtypes")
	private LenderController checkStationBikeCapacity(Station station)
			throws FullCapacityException {
		if (!(station.getBikes().size() == station.getMaximumCapacity())) {
			throw new FullCapacityException(station);
		}
		return this;
	}

	@SuppressWarnings("rawtypes")
	private LenderController checkCustomerCredit(Customer customer,
			Bicycle bicycle) throws NonPayAbilityException {
		if (customer.getCredit() < bicycle.getLendingPrice()) {
			throw new NonPayAbilityException(customer, bicycle);
		}
		return this;
	}

	private Lending createLending(Customer customer, Bicycle bicycle) {

		Lending lending = new Lending();

		lending.setBike(bicycle);
		lending.setCustomer(customer);

		return lending;
	}
	
	private LenderController addBikeToStation(Bicycle bike, Station station){
		station.addBike(bike);
		return this;
	}

	private LenderController removeBikeFromStation(Station station,
			Bicycle bicycle) throws NoSuchElement {
		station.removeBike(bicycle);
		return this;
	}

	private LenderController deductCredit(Customer customer, Bicycle bicycle)
			throws NoSuchElement {
		customer.setCredit(customer.getCredit() - bicycle.getLendingPrice());
		return this;
	}

	@SuppressWarnings("unchecked")
	private LenderController saveLending(Lending lending) {

		//this.dao.create((T) lending);
		
		return this;

	}

	// TODO: randomize
	private Bicycle getRandomBike(Station station) {
		return station.getBikes().get(0);
	}
	
	//public ...

	public void lendBicycle(Customer customer, Station station, Bicycle bicycle)
			throws NonPayAbilityException, EmptyStationException, NoSuchElement {

		Lending lending = checkStationBikes(station).checkCustomerCredit(
				customer, bicycle).createLending(customer, bicycle);

		removeBikeFromStation(station, bicycle).deductCredit(customer, bicycle).saveLending(lending);

	}

	public void lendRandomBicycle(Customer customer, Station station)
			throws NonPayAbilityException, EmptyStationException, NoSuchElement {

		Bicycle bicycle = checkStationBikes(station).getRandomBike(station);

		lendBicycle(customer, station, bicycle);

	}

	public void returnBicycle(Bicycle bike, Station station) throws FullCapacityException {		
		checkStationBikeCapacity(station).addBikeToStation(bike, station);		
	}

}