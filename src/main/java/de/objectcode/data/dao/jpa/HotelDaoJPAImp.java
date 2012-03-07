package de.objectcode.data.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.transaction.annotation.Transactional;

import de.objectcode.data.dao.interfaces.HotelDao;
import de.objectcode.data.dataobjects.Hotel;


public class HotelDaoJPAImp extends AbstractDaoJPAImpl<Hotel> implements HotelDao {
	
	public HotelDaoJPAImp() {
		super(Hotel.class);
	}

	@Transactional
	public List<Hotel> findAll() {
		return getJpaTemplate().execute(new JpaCallback<List<Hotel>>() {
			public List<Hotel> doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Hotel> query = em.createQuery("select e from Hotel e", Hotel.class);
				return query.getResultList();
			}
		});
	}

	@Transactional
	public List<Hotel> find(String searchString) {
		final String toSearch = searchString==null ?  "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
		return getJpaTemplate().execute(new JpaCallback<List<Hotel>>() {
			public List<Hotel> doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Hotel> query = em.createQuery("select h from Hotel h where lower(h.name) " +
						"like :toSearch or lower(h.city) like :toSearch or lower(h.zip) like :toSearch " +
						"or lower(h.address) like :toSearch", Hotel.class);
				query.setParameter("toSearch", toSearch);
				return query.getResultList();
			}
		});
	}
	
	
	@Transactional
	public int countAll() {
		return 0;
//		return getJpaTemplate().execute(new JpaCallback<Integer>() {
//
//			public Integer doInJpa(EntityManager em) throws PersistenceException {
//				TypedQuery<Long> query = em.createQuery("select count (e) from Hotel e", Long.class);
//				return (query.getSingleResult()).intValue();
//			}
//		});

	}
}
