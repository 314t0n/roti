package hu.elte.web.hajnaldavid.roti.persistence.migrations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle.BikeType;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;

public class DefaultMigration {
	
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public static void migrateAll() {
		
		CrudService<Station> stationDao = new GenericDao<Station>(
				Station.class);
		
		//bikeDao.create(getBikeInstance(1000, BikeType.CAMPING));			
		
		addStation(stationDao, 10, "Corvin negyed");
		addStation(stationDao, 12, "Deák Tér");
		addStation(stationDao, 10, "Blaha Lujza Tér");
		
		log4j.info("Migrated all ... ");		

	}
	
	public static void addStation(CrudService<Station> dao, int capacity, String name){
		
		Station station = getStationInstance(capacity, name)
			.addBike(getBikeInstance(1000, BikeType.CAMPING))
			.addBike(getBikeInstance(1000, BikeType.CAMPING))
			.addBike(getBikeInstance(1000, BikeType.CAMPING))
			.addBike(getBikeInstance(1000, BikeType.CITY))
			.addBike(getBikeInstance(1000, BikeType.CITY));
		
		dao.create(station);
	}
	
	private static Station getStationInstance(int capacity, String name){
		
		Station station = new Station();
		station.setMaximumCapacity(capacity);
		station.setName(name);
		
		return station;
		
	}

	
	private static Bicycle getBikeInstance(int price, Bicycle.BikeType type){

		Bicycle bike = new Bicycle();
		
		bike.setLendingPrice(price);
		bike.setType(type);
		return bike;
	}
}
