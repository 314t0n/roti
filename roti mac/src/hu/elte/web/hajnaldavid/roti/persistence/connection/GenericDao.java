package hu.elte.web.hajnaldavid.roti.persistence.connection;

import hu.elte.web.hajnaldavid.roti.Main;
import hu.elte.web.hajnaldavid.roti.persistence.entities.RotiEntity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenericDao<T extends RotiEntity> implements CrudService<T> {

	private static final Logger log4j = LogManager.getLogger(Main.class
			.getName());

	private final EntityManagerFactory emf;
	private final Class<T> type;

	public GenericDao(Class<T> type) {
		log4j.debug(type.toString());

		this.type = type;
		emf = Persistence.createEntityManagerFactory("roti mac");
	}

	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	@Override
	public List<T> readAll() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(type));
			Query q = em.createQuery(cq);
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void create(T t) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (read(new Long(t.getId()).intValue()) != null) {
				log4j.debug("Entity " + t + " already exists.");
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public T read(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(type, id.longValue());
		} finally {
			em.close();
		}
	}

	@Override
	public void update(T t) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			t = em.merge(t);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = new Long(t.getId()).intValue();
				if (read(id) == null) {
					log4j.debug("The entity with id " + id
							+ " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void delete(T t) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			try {
				t = em.getReference(type, t.getId());
				t.getId();
				em.remove(t);
				em.getTransaction().commit();
			} catch (EntityNotFoundException enfe) {
				log4j.debug("The entity no longer exists.");
			}
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public int rowCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<T> rt = cq.from(type);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
