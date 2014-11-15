package hu.elte.web.hajnaldavid.roti.graphics.tablemodels;

import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class TableModelFactory {

	public static <T extends RotiEntity> GenericTableModel createTableModel(
			Class<T> type, String[] propeties) throws InstantiationException,
			IllegalAccessException {

		if (type.equals(Station.class)) {
			StationDomain dao;
			dao = new StationDomain();
			return new StationTableModel(dao, propeties);
		} else {
			GenericDao<T> dao;
			dao = new GenericDao<>(type);
			return new GenericTableModel(dao, propeties);
		}
		
	}

}
