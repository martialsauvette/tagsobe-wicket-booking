package de.objectcode.data.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.transaction.annotation.Transactional;

import de.objectcode.data.dao.interfaces.UserDao;
import de.objectcode.data.dataobjects.User;


public class UserDaoJPAImp extends AbstractDaoJPAImpl<User> implements UserDao {

	
	public UserDaoJPAImp() {
		super(User.class);
	}

	@Transactional
	public List<User> findAll() {
		return getJpaTemplate().execute(new JpaCallback<List<User>>() {
			public List<User> doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<User> query = em.createQuery("select e from User e", User.class);
				return query.getResultList();
			}
		});
//		return null;
	}

	@Transactional
	public int countAll() {
//		return 0;
		return getJpaTemplate().execute(new JpaCallback<Integer>() {

			public Integer doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<Long> query = em.createQuery("select count (e) from Customer e", Long.class);
				return (query.getSingleResult()).intValue();
			}
		});

	}
	public boolean authenticate(final String userName, final String password) {
		return findUserByUserNameAndPassword(userName, password)!= null? true:false;
		

	}

	@Transactional
	public User findUserByUserNameAndPassword(final String userName, final String password) {
		return getJpaTemplate().execute(new JpaCallback<User>() {

			public User doInJpa(EntityManager em) throws PersistenceException {
				TypedQuery<User> query = em.createQuery("select u from User u where u.username = :userName and u.password = :password", User.class);
				query.setParameter("userName", userName);
				query.setParameter("password", password);
				return query.getSingleResult();
			}
		});
	}
}
