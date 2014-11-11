package hu.elte.web.hajnaldavid.roti.persistence.entities.builder;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class StationBuilder {

	private Station instance;		
	
	public StationBuilder() {	
		this.instance = new Station();
	}

	public Station getInstance(){
		return instance;
	}
	
	public StationBuilder setName(String name){
		instance.setName(name);
		return this;
	}
	
	public StationBuilder setMaximumCapacity(int maximumCapacity){
		instance.setMaximumCapacity(maximumCapacity);
		return this;
	}
	
	public StationBuilder addBike(Bicycle bike){
		instance.addBike(bike);
		return this;
	}
	
}
