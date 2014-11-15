package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;

public class DomainModelFactory {

	private static StationDomain stationDomain;
	private static BicycleDomain bicycleDomain;
	private static LendingDomain lendingDomain;

	public static GenericDao<? extends RotiEntity> getDomainModel(String name) {

		switch (name) {
		case "Station":
			if (stationDomain == null) {
				stationDomain = new StationDomain();
			}
			return stationDomain;
		case "Bicycle":
			if (bicycleDomain == null) {
				bicycleDomain = new BicycleDomain();
			}
			return stationDomain;
		case "Lending":
			if (lendingDomain == null) {
				lendingDomain = new LendingDomain();
			}
			return stationDomain;
		}
		return null;
	}

}
