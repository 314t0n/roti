package hu.elte.web.hajnaldavid.roti.logic.exceptions;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class FullCapacityException extends Exception {

	public FullCapacityException(Station station) {
		super(station.getName() + " is full.");
	}

}
