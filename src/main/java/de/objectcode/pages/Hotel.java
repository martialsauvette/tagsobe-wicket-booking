package de.objectcode.pages;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;

import de.objectcode.HotelBookingWebSession;
import de.objectcode.action.HotelBookingAction;

@AuthorizeInstantiation("ADMIN")
public class Hotel extends WebPage 
{

   private HotelBookingAction hotelBooking;

	public Hotel(final PageParameters parameters)
	{
	   super(parameters);
	   hotelBooking =  ((HotelBookingWebSession)this.getSession()).getHotelBookingAction() ;
	   Template body = new Template("body");
	   body.add(new Link("bookHotel")
	   {
	      @Override
	      public void onClick()
	      {
	         hotelBooking.bookHotel();
	         setResponsePage(new Book(new PageParameters()));
	      }
	   });
	   body.add(new Link("cancel")
      {
         @Override
         public void onClick()
         {
            setResponsePage(Main.class);
         }
         
      });
	   body.add(new HotelViewPanel("hotel", hotelBooking.getHotel()));
	   add(body);
	}
	
	@Override
	protected void onBeforeRender()
	{
	   super.onBeforeRender();
	}
	
	
}
