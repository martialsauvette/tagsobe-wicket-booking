package de.objectcode.pages;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.objectcode.HotelBookingWebSession;
import de.objectcode.action.HotelBookingAction;
import de.objectcode.data.dao.interfaces.BookingDao;
import de.objectcode.data.dataobjects.Booking;

@AuthorizeInstantiation("ADMIN")
public class Confirm extends WebPage 
{
   @SpringBean
	private BookingDao bookingDao;
   
   //@In
   private Booking booking;
   
   //@In(create=true)
   private HotelBookingAction hotelBooking;

	public Confirm(final PageParameters parameters)
	{
	   super(parameters);
	   hotelBooking =  ((HotelBookingWebSession)this.getSession()).getHotelBookingAction() ;
	   booking=hotelBooking.getBooking();
	   Template body = new Template("body");
	   body.add(new HotelViewPanel("hotel", booking.getHotel()));
	   body.add(new OutputBorder("totalBorder", "Total Payment", new Label("total", DecimalFormat.getCurrencyInstance(Locale.US).format(booking.getTotal()))));
	   body.add(new OutputBorder("checkinDateBorder", "Check in", new Label("checkinDate", new SimpleDateFormat().format(booking.getCheckinDate()))));
	   body.add(new OutputBorder("checkoutDateBorder", "Check out", new Label("checkoutDate", new SimpleDateFormat().format(booking.getCheckoutDate()))));
	   body.add(new OutputBorder("creditCardNumberBorder", "Credit Card #", new Label("creditCardNumber", booking.getCreditCard())));
	   body.add(new Link("revise")
      {
         @Override
         public void onClick()
         {
            setResponsePage(new Book(new PageParameters()));
         }
      });
      body.add(new Link("confirm")
      {
         @Override
         //@RaiseEvent("bookingConfirmed")
         public void onClick()
         {
        	 bookingDao.createBooking(booking);
        	 ((HotelBookingWebSession)this.getSession()).setBookings(
       			  bookingDao.findBookingsByUserId(((HotelBookingWebSession)this.getSession()).getUser().getUsername()));
            //hotelBooking.confirm();
            setResponsePage(Main.class);
         }
      });
      body.add(new Link("cancel")
      {
         @Override
         public void onClick()
         {
            //hotelBooking.cancel();
            setResponsePage(Main.class);
         }
         
      });
	   
	   add(body);
	}
	
}
