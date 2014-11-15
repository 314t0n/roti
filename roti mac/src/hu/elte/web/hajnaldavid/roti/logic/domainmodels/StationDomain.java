package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
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
		NORMAL(1.0), WARNING(0.5), ERROR(0.3);

		private Double statusValue;

		Status(Double rate) {
			statusValue = rate;
		}

		public Double getValue() {
			return this.statusValue;
		}
	}

	public Status getWarningLevelStatus(long id) {

		Station station = this.read(new Long(id).intValue());

		return getWarningLevelStatus(station);

	}

	public Status getWarningLevelStatus(Station station) {

		Double rate = (double) station.getBikes().size()
				/ (double) station.getMaximumCapacity();

		if (rate <= Status.ERROR.getValue()) {
			return Status.ERROR;
		}

		if (rate <= Status.WARNING.getValue()) {
			return Status.WARNING;
		}

		return Status.NORMAL;

	}

	public boolean transferBike(Station from, Station to)
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

		Bicycle bike = selectRandomBike(from);

		try {
			to.addBike(from.removeBike(bike));
			update(from);
			update(to);
		} catch (NoSuchElement e) {
			log4j.error(e.getMessage());
			return false;
		}

		return true;
	}

	public Bicycle selectRandomBike(Station station) {
		int numberOfBikes = station.getBikes().size();
		Random random = new Random();
		int index = random.nextInt(numberOfBikes);
		return station.getBikes().get(index);
	}

	public boolean addBike(Station station, Bicycle bicycle)
			throws FullCapacityException {

		if (station.getBikes().size() + 1 < station.getMaximumCapacity()) {

			station.addBike(bicycle);

			bicycle.setStation(station);

			update(station);

			return true;
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

	public void clearStation(Station station) {
		station.getBikes().clear();
		log4j.info(station + " has benn cleared!");
	}

}
