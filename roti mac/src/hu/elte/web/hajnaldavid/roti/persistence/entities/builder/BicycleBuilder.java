package hu.elte.web.hajnaldavid.roti.persistence.entities.builder;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;

public class BicycleBuilder {
	
private Bicycle instance;		
	
	public BicycleBuilder() {	
		this.instance = new Bicycle();
	}

	public Bicycle getInstance(){
		return instance;
	}

	public BicycleBuilder setLendingPrice(int lendingPrice){
		instance.setLendingPrice(lendingPrice);
		return this;
	}	
	
	public BicycleBuilder setType(Bicycle.BikeType type){
		instance.setType(type);
		return this;
	}		

}
