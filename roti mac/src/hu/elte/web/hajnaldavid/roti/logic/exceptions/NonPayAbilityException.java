package hu.elte.web.hajnaldavid.roti.logic.exceptions;

import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;

public class NonPayAbilityException extends Exception {

	public NonPayAbilityException(Customer customer, Bicycle bike) {
		super("[" + customer.getId() + "]" + customer.getName() + " tried to lend: " + bike.getId());		
	}

}
