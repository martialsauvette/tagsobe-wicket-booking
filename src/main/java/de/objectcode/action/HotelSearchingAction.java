package de.objectcode.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.objectcode.data.dao.interfaces.HotelDao;
import de.objectcode.data.dataobjects.Hotel;


@SuppressWarnings("serial")
@Component(value="hotelSearchingAction")
//@Scope("session")
public class HotelSearchingAction implements HotelSearching, Serializable
{
	@Autowired
	private HotelDao hotelDao;
   
   private String searchString;
   
   private List<Hotel> hotels = new ArrayList<Hotel>();
   
   public void find()
   {
	   hotels = hotelDao.find(searchString);
   }     
   
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
   
//   @Remove
   public void destroy() {}
}