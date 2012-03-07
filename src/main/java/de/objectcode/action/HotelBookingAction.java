package de.objectcode.action;

import static javax.persistence.PersistenceContextType.EXTENDED;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;

import de.objectcode.data.dataobjects.Booking;
import de.objectcode.data.dataobjects.Hotel;
import de.objectcode.data.dataobjects.User;

public class HotelBookingAction implements Serializable {

	private User user;

	private Hotel hotel;

	private Booking booking;

	// private StatusMessages statusMessages;

	private Log log;

	private boolean bookingValid;

	public void selectHotel(Hotel hotel) {
		// StatusMessages.instance().add("foo");
		this.hotel = hotel;
	}

	public void bookHotel() {
		booking = new Booking(hotel, user);
		Calendar calendar = Calendar.getInstance();
		booking.setCheckinDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		booking.setCheckoutDate(calendar.getTime());
	}

	public void setBookingDetails() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		if (booking.getCheckinDate().before(calendar.getTime())) {
			// statusMessages.addToControl("checkinDate",
			// "Check in date must be a future date");
			bookingValid = false;
		} else if (!booking.getCheckinDate().before(booking.getCheckoutDate())) {
			// statusMessages.addToControl("checkoutDate",
			// "Check out date must be later than check in date");
			bookingValid = false;
		} else {
			bookingValid = true;
		}
	}

	public boolean isBookingValid() {
		return bookingValid;
	}

	// public void confirm()
	// {
	// em.persist(booking);
	// //StatusMessages.instance().add("Thank you, #{user.name}, your confimation number for #{hotel.name} is #{booking.id}");
	// log.info("New booking: #{booking.id} for #{user.username}");
	// }

	public void setUser(User user) {
		this.user = user;
	}

	public Hotel getHotel() {
		return this.hotel;
	}

	public Booking getBooking() {
		return booking;
	}

}