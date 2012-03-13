package de.objectcode.pages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.yui.calendar.DateField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.ComponentFeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import de.objectcode.HotelBookingWebSession;
import de.objectcode.action.HotelBookingAction;
import de.objectcode.data.dataobjects.Booking;

@AuthorizeInstantiation("ADMIN")
public class Book extends WebPage 
{
   
   private static final List<String> bedOptionsDisplayValues = Arrays.asList("One king-sized bed", "Two double beds", "Three beds");
   private static final List<Integer> bedOptions = Arrays.asList(1, 2, 3);
   private static final List<String> monthOptions = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
   private static final List<Integer> yearOptions = Arrays.asList(2006, 2007, 2008, 2009, 2010, 2011, 2012);
   

   private Booking booking;
   

   private HotelBookingAction hotelBooking;

	public Book(final PageParameters parameters)
	{
	   super(parameters);
	   hotelBooking =  ((HotelBookingWebSession)this.getSession()).getHotelBookingAction() ;
	   booking=hotelBooking.getBooking();
	   Template body = new Template("body");
	   add(body);
	   body.add(new HotelViewPanel("hotelView", booking.getHotel()));
	   body.add(new HotelBookingForm("booking"));
	}
	
	public class HotelBookingForm extends Form
	{
	   
      public HotelBookingForm(String id)
      {
         super(id);
         add(new ComponentFeedbackPanel("messages", this));
         add(new FormInputBorder("checkinDateBorder", "Check in date", new DateField("checkinDate").setRequired(true), new PropertyModel(booking, "checkinDate"), false));
         add(new FormInputBorder("checkoutDateBorder", "Check out date", new DateField("checkoutDate").setRequired(true), new PropertyModel(booking, "checkoutDate"), false));
         add(new FormInputBorder("bedsBorder", "Room Preference", new DropDownChoice("beds", bedOptions, new IChoiceRenderer()
         {

            public Object getDisplayValue(Object object)
            {
               return bedOptionsDisplayValues.get(((Integer) object - 1));
            }

            public String getIdValue(Object object, int index)
            {
               return object.toString();
            }
            
         }
         ).setRequired(true), new PropertyModel(booking, "beds")));
         add(new FormInputBorder("smokingBorder", "Smoking Preference", new RadioChoice("smoking", Arrays.asList(new Boolean[] {true, false}), new IChoiceRenderer()
         {

            public Object getDisplayValue(Object object)
            {
               if (new Boolean(true).equals(object))
               {
                  return "Smoking";
               }
               else
               {
                  return "Non Smoking";
               }
            }

            public String getIdValue(Object object, int index)
            {
               if (new Boolean(true).equals(object))
               {
                  return "true";
               }
               else
               {
                  return "false";
               }
            }
            
         }), new PropertyModel(booking, "smoking"), false));
         add(new FormInputBorder("creditCardBorder", "Credit Card #", new TextField("creditCard").setRequired(true), new PropertyModel(booking, "creditCard")));
         add(new FormInputBorder("creditCardNameBorder", "Credit Card Name", new TextField("creditCardName").setRequired(true), new PropertyModel(booking, "creditCardName")));
         
         DropDownChoice<Integer> creditCardExpiryYear= new DropDownChoice<Integer>("creditCardExpiryYear", yearOptions, new IChoiceRenderer<Integer>(){
	           public Object getDisplayValue(Integer object)
	           {              
	               return object.toString();
	           }
	
	           public String getIdValue(Integer object, int index)
	           {
	               return object.toString();
	           }
      	 });
         
         add(new FormInputBorder("creditCardExpiryBorder", "Credit Card Expiry", 
        		 new DropDownChoice("creditCardExpiryMonth", monthOptions).setRequired(true), new PropertyModel(booking, "creditCardExpiryMonth"))
         .add(creditCardExpiryYear.setRequired(true), new PropertyModel(booking, "creditCardExpiryYear")));
         add(new Link("cancel")
         {

            @Override
            public void onClick()
            {
               setResponsePage(Main.class);
            }
            
         });          
      }
          
      
      @Override
      protected void onSubmit()
      {
         hotelBooking.setBookingDetails();
         if (hotelBooking.isBookingValid())
         {
            setResponsePage(new Confirm(new PageParameters()));
         }
      }
	}
	
}
