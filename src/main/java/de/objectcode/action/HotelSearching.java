//$Id: HotelSearching.java,v 1.12 2007/06/27 00:06:49 gavin Exp $
package de.objectcode.action;

import java.util.List;

import de.objectcode.data.dataobjects.Hotel;

//@Local
public interface HotelSearching
{
   
   public String getSearchString();
   public void setSearchString(String searchString);
   
   public String getSearchPattern();
   
   public List<Hotel> getHotels();
   
   public void find();

   public void destroy();
   
}