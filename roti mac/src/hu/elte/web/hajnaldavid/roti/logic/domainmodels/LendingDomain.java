package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LendingDomain<T extends Lending> extends GenericDao<T> {
	
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public LendingDomain(Class<T> type) {
		super(type);
	}
}
