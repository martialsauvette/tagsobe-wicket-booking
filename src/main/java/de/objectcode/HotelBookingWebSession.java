package de.objectcode;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Application;
import org.apache.wicket.Request;
import org.apache.wicket.authentication.AuthenticatedWebSession;
import org.apache.wicket.authorization.strategies.role.Roles;
import org.apache.wicket.proxy.IProxyTargetLocator;
import org.apache.wicket.proxy.LazyInitProxyFactory;

import de.objectcode.action.HotelBookingAction;
import de.objectcode.action.HotelSearchingAction;
import de.objectcode.data.dao.interfaces.UserDao;
import de.objectcode.data.dataobjects.Booking;
import de.objectcode.data.dataobjects.User;

@SuppressWarnings("serial")
public class HotelBookingWebSession extends AuthenticatedWebSession {
	/**
	 * Construct.
	 * 
	 * @param request
	 *            The current request object
	 */
	public HotelBookingWebSession(Request request) {
		super(request);
		hotelSearch = new HotelSearchingAction();
	}

	private HotelSearchingAction hotelSearch;

	public HotelSearchingAction getHotelSearchingAction() {
		if (hotelSearch == null)
			return new HotelSearchingAction();
		return hotelSearch;
	}
	
	private HotelBookingAction hotelBooking;

	public HotelBookingAction getHotelBookingAction() {
		if (hotelBooking == null){
			hotelBooking = new HotelBookingAction();
			hotelBooking.setUser(user);
		}
		return hotelBooking;
	}
	   
	private List<Booking> bookings;

	public  List<Booking>  getBookings(){
		if (bookings==null){
			bookings = new ArrayList<Booking>();
		}
		return bookings;
	}
	public void setBookings(List<Booking> toSet){
		this.bookings=toSet;
	}

	private User user;

	public User getUser() {
		return user;
	}

	private UserDao userDao = (UserDao) LazyInitProxyFactory.createProxy(
			UserDao.class, new IProxyTargetLocator() {
				public Object locateProxyTarget() {
					return ((WicketApplication) Application.get())
							.getApplicationContext().getBean("userDao");
				}
			});

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @see org.apache.wicket.authentication.AuthenticatedWebSession#authenticate(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public boolean authenticate(final String username, final String password) {
		this.user = userDao.findUserByUserNameAndPassword(username, password);
		return userDao.authenticate(username, password);
	}

	/**
	 * @see org.apache.wicket.authentication.AuthenticatedWebSession#getRoles()
	 */
	@Override
	public Roles getRoles() {
		if (isSignedIn()) {
			// If the user is signed in, they have these roles
			return new Roles(Roles.ADMIN);
		}
		return null;
	}
}
