package hu.elte.web.hajnaldavid.roti.persistence.entities.builder;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;

public class CustomerBuilder {
	
	private Customer instance;	
		
	public CustomerBuilder() {
		instance = new Customer();
	}

	public Customer getInstance(){
		return instance;
	}
	
	public CustomerBuilder setName(String name){
		instance.setName(name);
		return this;
	}
	
	public CustomerBuilder setCredit(Integer credit){
		instance.setCredit(credit);
		return this;
	}

}
