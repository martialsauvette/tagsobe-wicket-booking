package de.objectcode.data.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.transaction.annotation.Transactional;

import de.objectcode.data.dao.interfaces.BookingDao;
import de.objectcode.data.dataobjects.Booking;

public class BookingDaoJPAImp  extends AbstractDaoJPAImpl<Booking> implements BookingDao{

	public BookingDaoJPAImp() {
		super(Booking.class);
	}

	@Transactional
	public List<Booking> findAll() {
		return getJpaTemplate().execute(new JpaCallback<List<Booking>>() {
			public List<Booking> doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Booking> query = em.createQuery("select b from Booking b", Booking.class);
				return query.getResultList();
			}
		});
	}

	@Transactional
	public List<Booking> findBookingsByUserId(final String userName) {
		return getJpaTemplate().execute(new JpaCallback<List<Booking>>() {
			public List<Booking> doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Booking> query = em.createQuery("select b from Booking b where b.user.username = :userName", Booking.class);
				query.setParameter("userName", userName);
				return query.getResultList();
			}
		});
	}

	public int countAll() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Transactional
	public void createBooking(final Booking booking) {
		getJpaTemplate().execute(new JpaCallback<Booking>() {
	        public Booking doInJpa(EntityManager entityManager) throws PersistenceException {
	            entityManager.persist(booking);
	            entityManager.flush();
	            return null;
	        }
		});
		
	}

}
