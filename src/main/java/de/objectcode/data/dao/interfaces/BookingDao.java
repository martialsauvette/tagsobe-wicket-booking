package de.objectcode.data.dao.interfaces;

import java.util.List;

import de.objectcode.data.dataobjects.Booking;

public interface BookingDao extends Dao<Booking>{
	
	List<Booking> findBookingsByUserId(final String userName);
	
	void createBooking(Booking booking);
	
	void cancelBooking(final Booking booking);
}
