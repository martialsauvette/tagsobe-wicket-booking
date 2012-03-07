/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.objectcode.pages;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.objectcode.HotelBookingWebSession;
import de.objectcode.action.HotelBookingAction;
import de.objectcode.action.HotelSearchingAction;
import de.objectcode.data.dao.interfaces.HotelDao;
import de.objectcode.data.dataobjects.Booking;
import de.objectcode.data.dataobjects.Hotel;



@AuthorizeInstantiation("ADMIN")
public class Main extends WebPage
{

	@SpringBean
	private HotelDao hotelDao;
	
	//@SpringBean(name="hotelSearchingAction")
   private HotelSearchingAction hotelSearch;
//	private String hotelSearch;
//   
////   @In(create=true)
   private List<Booking> bookings;
//   
////   @In(create=true)
//   private BookingList bookingList;
//   
////   @In(create=true)
   private HotelBookingAction hotelBooking;

   private DataView       hotelDataView;
   private DataView       bookedHotelDataView;
   private HotelSearchForm hotelSearchForm;
   private WebMarkupContainer hotels;
   private Component noHotelsFound;
   private Component messages;
   
   
   public Main(final PageParameters parameters)
   {	   
	  hotelSearch=  ((HotelBookingWebSession)this.getSession()).getHotelSearchingAction() ;
	  hotelBooking=  ((HotelBookingWebSession)this.getSession()).getHotelBookingAction() ;
      //hotelSearch = new HotelSearchingAction();
      //hotelBooking = new HotelBookingAction();
      Template body = new Template("body");
      add(body);
      hotelSearchForm = new HotelSearchForm("searchCriteria");
      body.add(hotelSearchForm);
      
      messages = new FeedbackPanel("messages", new ContainerFeedbackMessageFilter(this)).setOutputMarkupId(true);
      body.add(messages);
      
      /*
       * Hotel Search results
       */
      noHotelsFound = new Label("noResults", "No Hotels Found")
      {
         /**
          * Only display the message if no hotels are found
          * 
          */
         @Override
         public boolean isVisible()
         {//return true;
            return  hotelSearch.getHotels().size() == 0;
         }
      };
      body.add(noHotelsFound.setOutputMarkupId(true));
      hotelDataView = new DataView("hotel", new SimpleDataProvider() // A DataProvider adapts between your data and Wicket's internal representation
      {
         public Iterator iterator(int from, int count)
         {
            return hotelSearch.getHotels().subList(from, from + count).iterator();
         }

         public int size()
         {
            return hotelSearch.getHotels().size();
         }

      })
      {
         /**
          * You specify the tr in the html, and populate each one here
          */
         @Override
         protected void populateItem(Item item)
         {
            final Hotel hotel = (Hotel) item.getModelObject();
            item.add(new Label("hotelName", hotel.getName()));
            item.add(new Label("hotelAddress", hotel.getAddress()));
            item.add(new Label("hotelCityStateCountry", hotel.getCity() + ", " + hotel.getState() + ", " + hotel.getCountry()));
            item.add(new Label("hotelZip", hotel.getZip()));
            item.add(new Link("viewHotel")
            {

//							 @Begin
               @Override
               public void onClick()
               {
                  hotelBooking.selectHotel(hotel);
                  setResponsePage(new de.objectcode.pages.Hotel(new PageParameters()));
               }
            
            });
         }
         
      };
      
      // Set the maximum items per page
      hotelDataView.setItemsPerPage(hotelSearchForm.getPageSize());
      hotelDataView.setOutputMarkupId(true);
      hotels = new WebMarkupContainer("hotels");
      hotels.add(hotelDataView).setOutputMarkupId(true);
      
      
      // Add a pager
      hotels.add(new AjaxPagingNavigator("hotelPager", hotelDataView)
      {
         @Override
         public boolean isVisible()
         {
            return hotelDataView.isVisible();
         }

      });
      
      body.add(hotels);
      
      /*
       * Existing hotel booking
       */
      bookedHotelDataView = new DataView("bookedHotel", new SimpleDataProvider()
      {
         public Iterator iterator(int from, int count)
         {return null;
            //return bookings.subList(from, from + count).iterator();
         }

         public int size()
         { return 0;
            //return bookings.size();
         }
         
         

      })
      {

         @Override
         protected void populateItem(Item item)
         {
            final Booking booking = (Booking) item.getModelObject();
            item.add(new Label("hotelName", booking.getHotel().getName()));
            item.add(new Label("hotelAddress", booking.getHotel().getAddress()));
            item.add(new Label("hotelCityStateCountry", booking.getHotel().getCity() + ", " + booking.getHotel().getState() + ", " + booking.getHotel().getState()));
            item.add(new Label("hotelCheckInDate", booking.getCheckinDate().toString()));
            item.add(new Label("hotelCheckOutDate", booking.getCheckoutDate().toString()));
            item.add(new Label("hotelConfirmationNumber", booking.getId().toString()));
            item.add(new Link("cancel")
            {

               @Override
               public void onClick()
               {
                  //bookingList.cancel(booking);
               }
               
            });
         }
         
         @Override
         public boolean isVisible()
         {
        	 return false;
             // return Identity.instance().isLoggedIn() && bookings.size() > 0;
         }

      };
      body.add(bookedHotelDataView);
      body.add(new Label("noHotelsBooked", "No Bookings Found")
      {
         @Override
         public boolean isVisible()
         {return false;
           // return Identity.instance().isLoggedIn() && bookings.size() == 0;
         }
      });
   }

   public class HotelSearchForm extends Form
   {
      private Integer pageSize = 10;
      
      public Integer getPageSize()
      {
         return pageSize;
      }
      public  String getSearchString(){
    	  return hotelSearch.getSearchString();
      }
      public  void setSearchString(String aString){
    	  hotelSearch.setSearchString(aString);
      }
      public void setPageSize(Integer pageSize)
      {
         this.pageSize = pageSize;
      }
      
      public HotelSearchForm(String id)
      {
         super(id);
//  	   	hotelSearch = new HotelSearchingAction();
         add(new TextField("searchString", new PropertyModel(this,"searchString")
         {
            
            @Override
            public Object getObject()
            {
               return hotelSearch;
            }
         
         }));
         List<Integer> pageSizes = Arrays.asList(new Integer[] { 5, 10, 20 });
         add(new DropDownChoice("pageSize", new PropertyModel(this, "pageSize"), pageSizes));
         add(new AjaxButton("submit", this)
         {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form form)
            {
               target.addComponent(messages);
               hotelSearch.setHotels(hotelDao.find(hotelSearch.getSearchPattern()));
               hotelDataView.setCurrentPage(0);
               hotelDataView.setItemsPerPage(getPageSize());
               hotelDataView.modelChanged();
               hotels.modelChanged();
               target.addComponent(hotels);
               target.addComponent(noHotelsFound);
            }
            
            @Override
            protected void onError(AjaxRequestTarget target, Form form)
            {
               target.addComponent(messages);
            }
            
         });
      }

      @Override
      protected void onSubmit()
      {
//         hotelDataView.setCurrentPage(0);
//         hotelDataView.setItemsPerPage(getPageSize());
    	  hotelSearch.setHotels(hotelDao.find(hotelSearch.getSearchPattern()));
      }

   }
}


