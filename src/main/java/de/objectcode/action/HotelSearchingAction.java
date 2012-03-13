package de.objectcode.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.objectcode.data.dataobjects.Hotel;


@SuppressWarnings("serial")
public class HotelSearchingAction implements Serializable
{
   
   private String searchString="";
   
   private List<Hotel> hotels = new ArrayList<Hotel>();
     
   
   public String getSearchPattern()
   {
      return searchString==null ? 
            "%" : '%' + searchString.toLowerCase().replace('*', '%') + '%';
   }
   
   public String getSearchString()
   {
      return searchString;
   }
   
   public void setSearchString(String searchString)
   {
      this.searchString = searchString;
   }
   
   public List<Hotel> getHotels()
   {
      return hotels;
   }
   
public void setHotels(List<Hotel> hotels) {
	this.hotels = hotels;
}

}