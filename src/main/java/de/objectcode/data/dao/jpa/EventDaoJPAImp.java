package de.objectcode.data.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.transaction.annotation.Transactional;

import de.objectcode.data.dao.interfaces.EventDao;
import de.objectcode.data.dataobjects.Event;

/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public class EventDaoJPAImp extends AbstractDaoJPAImpl<Event> implements EventDao {

	public EventDaoJPAImp() {
		super(Event.class);
	}

	@Transactional
	public List<Event> findAll() {
		return getJpaTemplate().execute(new JpaCallback<List<Event>>() {
			public List<Event> doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Event> query = em.createQuery("select e from Event e", Event.class);
				return query.getResultList();
			}
		});
	}

	@Transactional
	public int countAll() {
		return getJpaTemplate().execute(new JpaCallback<Integer>() {

			public Integer doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Long> query = em.createQuery("select count (e) from Event e", Long.class);
				return (query.getSingleResult()).intValue();
			}
		});

	}
}
