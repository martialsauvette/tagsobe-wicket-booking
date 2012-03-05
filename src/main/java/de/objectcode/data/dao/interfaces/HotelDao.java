package de.objectcode.data.dao.interfaces;

import java.util.List;

import de.objectcode.data.dataobjects.Hotel;
/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public interface HotelDao extends Dao<Hotel> {
	List<Hotel> find(String stringSearch);
}
