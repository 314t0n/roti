package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LendingDomain extends GenericDao<Lending> {
	
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public LendingDomain() {
		super(Lending.class);
	}
	

	private LendingDomain checkStationBikes(Station station)
			throws EmptyStationException {
		if (!(station.getBikes().size() > 0)) {
			throw new EmptyStationException(station);
		}
		return this;
	}
	

	private LendingDomain checkStationBikeCapacity(Station station)
			throws FullCapacityException {
		if (!(station.getBikes().size() == station.getMaximumCapacity())) {
			throw new FullCapacityException(station);
		}
		return this;
	}

	
	private LendingDomain checkCustomerCredit(Customer customer,
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
		lending.setRevenue(bicycle.getLendingPrice());

		return lending;
	}
	
	private LendingDomain addBikeToStation(Bicycle bike, Station station){
		station.addBike(bike);
		return this;
	}

	private LendingDomain removeBikeFromStation(Station station,
			Bicycle bicycle) throws NoSuchElement {
		station.removeBike(bicycle);
		return this;
	}

	private LendingDomain deductCredit(Customer customer, Bicycle bicycle)
			throws NoSuchElement {
		customer.setCredit(customer.getCredit() - bicycle.getLendingPrice());
		return this;
	}

	private LendingDomain saveLending(Lending lending) {

		this.create(lending);
		
		return this;

	}

	// TODO: randomize
	private Bicycle getRandomBike(Station station) {
		return station.getBikes().get(0);
	}
	
	//public ...

	public Lending lendBicycle(Customer customer, Station station, Bicycle bicycle)
			throws NonPayAbilityException, EmptyStationException, NoSuchElement {

		Lending lending = checkStationBikes(station).checkCustomerCredit(
				customer, bicycle).createLending(customer, bicycle);

		removeBikeFromStation(station, bicycle).deductCredit(customer, bicycle).saveLending(lending);
		
		return lending;

	}

	public Lending lendRandomBicycle(Customer customer, Station station)
			throws NonPayAbilityException, EmptyStationException, NoSuchElement {

		Bicycle bicycle = checkStationBikes(station).getRandomBike(station);
		
		customer.setBicycle(bicycle);
	

		return lendBicycle(customer, station, bicycle);

	}

	public void returnBicycle(Bicycle bike, Station station) throws FullCapacityException {		
		checkStationBikeCapacity(station).addBikeToStation(bike, station);		
	}

}
