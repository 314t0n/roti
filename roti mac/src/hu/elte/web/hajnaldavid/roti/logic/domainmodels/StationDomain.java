package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StationDomain extends GenericDao<Station> {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public StationDomain() {
		super(Station.class);
	}

	public enum Status {
		NORMAL(1.0, "Rendben"), WARNING(0.5, "Alacsony"), ERROR(0.3,
				"Vészesen alacsony");

		private Double statusValue;
		private String stringValue;

		Status(Double rate, String name) {
			statusValue = rate;
			stringValue = name;
		}

		public Double getValue() {
			return this.statusValue;
		}

		@Override
		public String toString() {
			return stringValue;
		}

	}

	public Status getWarningLevelStatus(long id) {

		Station station = this.read(new Long(id).intValue());

		return getWarningLevelStatus(station);

	}

	public Status getWarningLevelStatus(Station station) {

		Double rate = calculateRate(station);

		if (rate <= Status.ERROR.getValue()) {
			return Status.ERROR;
		}

		if (rate <= Status.WARNING.getValue()) {
			return Status.WARNING;
		}

		return Status.NORMAL;

	}

	private Double calculateRate(Station station) {
		return (double) station.getBikes().size()
				/ (double) station.getMaximumCapacity();
	}

	public synchronized Station returnBicycle(Customer customer, Station station)
			throws FullCapacityException {
		
		checkStationBikeCapacity(station);
		addBikeToStation(customer.getBicycle(), station);
		removBikeFromCustomer(customer);

		return update(station);

	}

	public boolean transferBike(Station from, Station to, int amount)
			throws EmptyStationException, FullCapacityException {

		if (from.equals(to)) {
			return false;
		}

		if (!(from.getBikes().size() > 0)) {
			throw new EmptyStationException(from);
		}

		if (!(to.getBikes().size() < to.getMaximumCapacity())) {
			throw new FullCapacityException(to);
		}

		for (int i = 0; i < amount; i++) {

			Bicycle bike = selectRandomBike(from);

			try {

				to.addBike(from.removeBike(bike));
				bike.setStation(to);

			} catch (NoSuchElement e) {
				log4j.error(e.getMessage());
				return false;
			}

		}

		return true;
	}

	public Bicycle selectRandomBike(Station station) {
		int numberOfBikes = station.getCurrentCapacity();
		Random random = new Random();
		int index = random.nextInt(numberOfBikes);
		return station.getBikes().get(index);
	}

	public synchronized Station addBike(Station station, Bicycle bicycle)
			throws FullCapacityException {

		if (station.getCurrentCapacity() < station.getMaximumCapacity()) {

			station.addBicycle(bicycle);

			return update(station);
		}

		throw new FullCapacityException(station);

	}

	public Station findByName(String name) {

		Station station = readAll()
				.stream()
				.filter(s -> s.getName().toLowerCase()
						.equals(name.toLowerCase())).findFirst().get();

		if (station == null) {
			throw new NullPointerException("Station " + name + " not found");
		}

		return station;

	}

	public Station clearStation(Station station) {
		log4j.info(station + " has been cleared!");
		station.getBikes().clear();
		return update(station);		
	}

	private synchronized StationDomain addBikeToStation(Bicycle bike,
			Station station) {
		station.addBike(bike);
		bike.setStation(station);
		return this;
	}

	private StationDomain removBikeFromCustomer(Customer customer) {
		Bicycle bicycle = customer.getBicycle();
		bicycle.setCustomer(null);
		customer.setBicycle(null);
		return this;
	}

	private StationDomain checkStationBikeCapacity(Station station)
			throws FullCapacityException {
		if ((station.getBikes().size() == station.getMaximumCapacity())) {
			throw new FullCapacityException(station);
		}
		return this;
	}
}
