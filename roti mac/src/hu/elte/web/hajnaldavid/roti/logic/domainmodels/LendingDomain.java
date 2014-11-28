package hu.elte.web.hajnaldavid.roti.logic.domainmodels;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.EmptyStationException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.FullCapacityException;
import hu.elte.web.hajnaldavid.roti.logic.exceptions.NonPayAbilityException;
import hu.elte.web.hajnaldavid.roti.persistence.connection.GenericDao;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Bicycle;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Customer;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Lending;
import hu.elte.web.hajnaldavid.roti.persistence.entities.Station;
import hu.elte.web.hajnaldavid.roti.persistence.exception.NoSuchElement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LendingDomain extends GenericDao<Lending> {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	public LendingDomain() {
		super(Lending.class);
	}

	private LendingDomain checkStationBikes(Station station)
			throws EmptyStationException {
		if (!(station.getBikes().size() > 0)) {
			throw new EmptyStationException(station);
		}
		return this;
	}

	private LendingDomain checkCustomerCredit(Customer customer, Bicycle bicycle)
			throws NonPayAbilityException {
		if (customer.getCredit() < bicycle.getLendingPrice()) {
			throw new NonPayAbilityException(customer, bicycle);
		}
		return this;
	}

	private Lending createLending(Customer customer, Bicycle bicycle) {

		Lending lending = new Lending();

		bicycle.setCustomer(customer);
		customer.setBicycle(bicycle);

		lending.setBike(bicycle);
		lending.setCustomer(customer);
		lending.setRevenue(bicycle.getLendingPrice());

		return lending;
	}

	private LendingDomain removeBikeFromStation(Station station, Bicycle bicycle)
			throws NoSuchElement {
		station.removeBike(bicycle);
		return this;
	}

	private LendingDomain deductCredit(Customer customer, Bicycle bicycle)
			throws NoSuchElement {
		customer.setCredit(customer.getCredit() - bicycle.getLendingPrice());
		return this;
	}

	private Lending saveLending(Lending lending) {

		return this.create(lending);

	}

	// TODO: randomize
	private Bicycle getRandomBike(Station station) {
		return station.getBikes().get(0);
	}

	// public ...

	public synchronized Lending lendBicycle(Customer customer, Station station,
			Bicycle bicycle) throws NonPayAbilityException,
			EmptyStationException, NoSuchElement {

		Lending lending = checkStationBikes(station).checkCustomerCredit(
				customer, bicycle).createLending(customer, bicycle);

		removeBikeFromStation(station, bicycle).deductCredit(customer, bicycle);				

		return saveLending(lending);

	}

	public synchronized Lending lendRandomBicycle(Customer customer, Station station)
			throws NonPayAbilityException, EmptyStationException, NoSuchElement {

		Bicycle bicycle = checkStationBikes(station).getRandomBike(station);
		
		return lendBicycle(customer, station, bicycle);

	}

	
	public synchronized Bicycle getBicycleByCustomer(Customer customer) {

		EntityManager em = getEntityManager();

		try {

			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
			Root from = criteriaQuery.from(Lending.class);
			CriteriaQuery<Object> select = criteriaQuery.select(from);

			Predicate predicate = criteriaBuilder.equal(from.get("customer"),
					customer);

			criteriaQuery.where(predicate);

			TypedQuery<Object> typedQuery = em.createQuery(select);
			List<Object> resultList = typedQuery.getResultList();

			if (resultList.size() == 0) {
				throw new NoSuchElement(customer);
			}

			return ((Lending) resultList.get(0)).getBike();

		} catch (Exception ex) {
			log4j.error(ex.getMessage());

		} finally {
			em.close();
		}

		return null;

	}
}
