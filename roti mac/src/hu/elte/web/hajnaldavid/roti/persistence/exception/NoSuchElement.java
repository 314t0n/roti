package hu.elte.web.hajnaldavid.roti.persistence.exception;

import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;

public class NoSuchElement extends Exception {

	private static final long serialVersionUID = 1L;

	public NoSuchElement(RotiEntity entity){
		super("No Such Element: " + entity.toString());
	}
	
	public NoSuchElement(int index){
		super("No Such Element at [ " + index + "]");
	}

}
