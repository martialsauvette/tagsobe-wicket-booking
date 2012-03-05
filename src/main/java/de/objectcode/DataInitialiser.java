package de.objectcode;

import java.util.ArrayList;
import java.util.List;

import de.objectcode.data.dao.interfaces.HotelDao;
import de.objectcode.data.dao.interfaces.UserDao;
import de.objectcode.data.dataobjects.Event;
import de.objectcode.data.dataobjects.Hotel;
import de.objectcode.data.dataobjects.User;

/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public class DataInitialiser {
	
	private static String[] dummyTitles = {"Wicket Event", "Party", "Breakfast At Tiffany's", "Holiday"};
	private static String[] dummyLocations = {"London", "Paris", "Pub", "New York"};
	

	private UserDao userDao;
	private HotelDao hotelDao;
	
	public void dataInit()
	{
		
        List<Hotel> hotels = new ArrayList<Hotel>();
        List<User> users = new ArrayList<User>();
        
        users.add(new User("totototo tutututu", "totototo", "tutututu"));       
        users.add(new User("keith keith", "keith", "melbourne"));
        

        hotels.add(new Hotel(129,  "Marriott Courtyard", "Tower Place, Buckhead", "Atlanta",
                "GA", "30305", "USA"));

        hotels.add(new Hotel(84,  "Doubletree Atlanta-Buckhead", "3342 Peachtree Road NE",
                "Atlanta", "GA", "30326", "USA"));

        hotels.add(new Hotel(289,  "W New York - Union Square", "201 Park Avenue South",
                "New York", "NY", "10003", "USA"));

        hotels.add(new Hotel(219,  "W New York", "541 Lexington Avenue", "New York", "NY",
                "10022", "USA"));

        hotels.add(new Hotel(250,  "Hotel Rouge", "1315 16th Street NW", "Washington", "DC",
                "20036", "USA"));

        hotels.add(new Hotel(159,  "70 Park Avenue Hotel", "70 Park Avenue, 38th St", "New York",
                "NY", "10016", "USA"));

        hotels.add(new Hotel(198,  "Parc 55", "55 Cyril Magnin Street", "San Francisco", "CA",
                "94102", "USA"));

//        hotels.add(new Hotel(189,  "Conrad Miami", "1395 Brickell Ave", "Miami", "FL", "33131",
//                "USA"));
//
//        hotels.add(new Hotel(111,  "Grand Hyatt", "345 Stockton Street", "San Francisco", "CA",
//                "94108", "USA"));
//
//        hotels.add(new Hotel(54,  "Super 8 Eau Claire Campus Area", "1151 W MacArthur Ave",
//                "Eau Claire", "WI", "54701", "USA"));
//
//        hotels.add(new Hotel(199,  "San Francisco Marriott", "55 Fourth Street", "San Francisco",
//                "CA", "94103", "USA"));
//
//        hotels.add(new Hotel(543,  "Hilton Diagonal Mar", "Passeig del Taulat 262-264",
//                "Barcelona", "Catalunya", "08019", "ES"));
//
//        hotels.add(new Hotel(335,  "Hilton Tel Aviv", "Independence Park", "Tel Aviv", null,
//                "63405", "IL"));
//
//        hotels.add(new Hotel(242,  "InterContinental Hotel Tokyo Bay", "1-15-2 Kaigan", "Tokyo",
//                "Minato", "105", "JP"));
//
//        hotels.add(new Hotel(130, "Hotel Beaulac", " Esplanade Léopold-Robert 2", "Neuchatel",
//                null, "2000", "CH"));
//
//        hotels.add(new Hotel(266, "Conrad Treasury Place", "130 William Street", "Brisbane",
//                "QL", "4001", "AU"));
//
//        hotels.add(new Hotel(170, "Ritz-Carlton Montreal", "1228 Sherbrooke St West",
//                "Montreal", "Quebec", "H3G1H6", "CA"));
//
//        hotels.add(new Hotel(179, "Ritz-Carlton Atlanta", "181 Peachtree St NE", "Atlanta",
//                "GA", "30303", "USA"));
//
//        hotels.add(new Hotel(145, "Swissotel Sydney", "68 Market Street", "Sydney", "NSW",
//                "2000", "AU"));
//
//        hotels.add(new Hotel(178, "Melié White House", "Albany Street Regents Park", "London",
//                null, "NW13UP", "GB"));
//
//        hotels.add(new Hotel(159, "Hotel Allegro", "171 W Randolph Street", "Chicago", "IL",
//                "60601", "USA"));
//
//        hotels.add(new Hotel(296, "Caesars Palace", "3570 Las Vegas Blvd S", "Las Vegas", "NV",
//                "89109", "USA"));
//
//        hotels.add(new Hotel(300, "Mandalay Bay Resort & Casino", "3950 Las Vegas Blvd S",
//                "Las Vegas", "NV", "89119", "USA"));
//
//        hotels.add(new Hotel(100, "Hotel Cammerpoorte", "Nationalestraat 38-40", "Antwerp",
//                null, "2000", "BE"));

        createUser(users);
        createHotel(hotels);
		
		
		
		
		
	}
    private void createHotel(List<Hotel> entities)
    {
        for (Hotel e : entities)
        {
        	hotelDao.save(e);
        }
    }
    private void createUser(List<User> entities)
    {
        for (User e : entities)
        {
        	userDao.save(e);
        }
    }
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public void setHotelDao(HotelDao hotelDao) {
		this.hotelDao = hotelDao;
	}
			

}
