package hu.elte.web.hajnaldavid.roti.tests.persistence;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.domainmodels.StationDomain;
import hu.elte.web.hajnaldavid.roti.persistence.connection.CrudService;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle.BikeType;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.entities.builder.CustomerBuilder;
import hu.elte.web.hajnaldavid.roti.persistence.migrations.DefaultMigration;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class DBTest {

	private static boolean needMigration = false;
	private static boolean needCleanUp = false;
	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());


	@After
	public void cleanup() {
		if (needCleanUp) {
			EntityManager em = Persistence.createEntityManagerFactory(
					"roti mac").createEntityManager();
			em.getTransaction().begin();
			em.createNativeQuery("truncate table bicycle cascade")
					.executeUpdate();
			em.createNativeQuery("truncate table station cascade")
					.executeUpdate();
			em.getTransaction().commit();
		}
	}

	@Test
	public void testMigration() {

		if (needMigration) {

			DefaultMigration.migrateAll();

			CrudService<Station> stationDao = new GenericDao<Station>(
					Station.class);

			Assert.assertEquals(3, stationDao.rowCount());

			Station station = stationDao.readAll().get(0);

			Assert.assertEquals("Corvin negyed", station.getName());

			Assert.assertEquals(5, station.getBikes().size());

			Assert.assertEquals(BikeType.CAMPING, station.getBikes().get(0)
					.getType());

		}

	}
	
	// nem determinisztikus
	@Test
	public void testSelectRandomBike() {	
		
		DefaultMigration.migrateAll();

		StationDomain stationDao = new StationDomain();
		
		log4j.info(stationDao.readAll().get(0));

		Station station = stationDao.readAll().get(0);		

		log4j.debug(station.getBikes().get(0));

		Bicycle bike1 = stationDao.selectRandomBike(station);
		Bicycle bike2 = stationDao.selectRandomBike(station);

		//Assert.assertNotEquals(bike1.getId(), bike2.getId());

	}

	@Test
	public void testTransferBike() {	
		
		DefaultMigration.migrateAll();

		StationDomain stationDao = new StationDomain();		

		Station stationA = stationDao.readAll().get(0);		
		Station stationB = stationDao.readAll().get(1);		
		
		stationA.addBike(new Bicycle());
		
		int bikesOfA = stationA.getBikes().size();
		int bikesOfB = stationB.getBikes().size();

		stationDao.transferBike(stationA, stationB);
		
		Assert.assertEquals(stationA.getBikes().size(), bikesOfA-1);
		Assert.assertEquals(stationB.getBikes().size(), bikesOfB+1);
		

	}

	@Test
	public void testCustomerDao() {

		CrudService<Customer> customerDao = new GenericDao<Customer>(
				Customer.class);

		Customer customer = new CustomerBuilder().setName("Elek Teszt")
				.setCredit(1000).getInstance();

		customerDao.create(customer);

		Assert.assertEquals(customerDao.rowCount(), 1);

		customerDao.delete(customer);

		Assert.assertEquals(customerDao.rowCount(), 0);

	}

}
