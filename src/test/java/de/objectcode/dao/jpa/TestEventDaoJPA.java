package de.objectcode.dao.jpa;


import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.objectcode.data.dao.interfaces.EventDao;
import de.objectcode.data.dao.interfaces.HotelDao;
import de.objectcode.data.dao.interfaces.UserDao;
import de.objectcode.data.dataobjects.Event;
import de.objectcode.data.dataobjects.Hotel;
import de.objectcode.data.dataobjects.User;

/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TestEventDaoJPA {

	@Autowired
	private EventDao eventDao;
	@Autowired
	private HotelDao hotelDao;
	@Autowired
	private UserDao userDao;

	protected Event event;

	@Before
	public void startTransaction()
	{
		event = new Event();
		event.setLocation("new Location");
		event.setTitle("new Title");
		event = eventDao.save(event);
	}


	/**
	 * Test method for {@link de.objectcode.data.dao.jpa.EventDaoJPAImp#findAll()}.
	 */
	@Test
	@Transactional
	@Rollback
	public void testFindAll() {
		List<Event> events = new ArrayList<Event>();
		events.add(event);
		Assert.assertEquals(events, eventDao.findAll());
		List<Hotel> hotel = hotelDao.findAll();
		List<Hotel> hotelList = hotelDao.find("dou");
		
		List<User> users = userDao.findAll();
		
		boolean test = userDao.authenticate("keith", "melbourne");
		
	}

	/**
	 * Test method for {@link de.objectcode.data.dao.jpa.EventDaoJPAImp#countAll()}.
	 */
	@Test @Transactional
	@Rollback
	public void testCountAll() {
		//Assert.assertEquals(1, eventDao.countAll());
	}

	/**
	 * Test method for {@link de.objectcode.data.dao.jpa.AbstractDaoJPAImpl#delete(de.objectcode.data.dataobjects.DomainObject)}.
	 */
	@Test @Transactional
	@Rollback
	public void testDelete() {
		eventDao.delete(event);
		//Assert.assertEquals(0, eventDao.countAll());
	}

	/**
	 * Test method for {@link de.objectcode.data.dao.jpa.AbstractDaoJPAImpl#load(java.io.Serializable)}.
	 */
	@Test @Transactional
	@Rollback
	public void testLoad() {
		Event event2 = eventDao.load(event.getId());
		Assert.assertEquals(event, event2);
	}

	/**
	 * Test method for {@link de.objectcode.data.dao.jpa.AbstractDaoJPAImpl#save(de.objectcode.data.dataobjects.DomainObject)}.
	 */
	@Test @Transactional
	@Rollback
	public void testSave() {
		//if we have got this far then save works
	}

}
