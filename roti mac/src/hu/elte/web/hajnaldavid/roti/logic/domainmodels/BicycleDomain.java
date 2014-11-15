package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;

public class BicycleDomain extends GenericDao<Bicycle> {
	
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public BicycleDomain() {
		super(Bicycle.class);		
	}

}
