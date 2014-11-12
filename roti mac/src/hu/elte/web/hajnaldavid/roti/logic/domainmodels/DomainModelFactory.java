package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class DomainModelFactory {

	private static StationDomain<Station> stationDomain;
	private static BicycleDomain<Bicycle> bicycleDomain;
	private static LendingDomain<Lending> lendingDomain;

	public static GenericDao<? extends RotiEntity> getDomainModel(String name) {

		switch (name) {
		case "Station":
			if (stationDomain == null) {
				stationDomain = new StationDomain<Station>(Station.class);
			}
			return stationDomain;
		case "Bicycle":
			if (bicycleDomain == null) {
				bicycleDomain = new BicycleDomain<Bicycle>(Bicycle.class);
			}
			return stationDomain;
		case "Lending":
			if (lendingDomain == null) {
				lendingDomain = new LendingDomain<Lending>(Lending.class);
			}
			return stationDomain;
		}
		return null;
	}

}
