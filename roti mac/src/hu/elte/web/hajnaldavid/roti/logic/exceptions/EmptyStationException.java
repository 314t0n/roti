package hu.elte.web.hajnaldavid.roti.logic.exceptions;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class EmptyStationException extends Exception {

	public EmptyStationException(Station station) {
		super(station.getName() + " is empty.");		
	}	
	
}
