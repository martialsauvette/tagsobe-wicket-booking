package de.objectcode.data.dao.interfaces;

import java.util.List;

import de.objectcode.data.dataobjects.Hotel;

public interface HotelDao extends Dao<Hotel> {
	List<Hotel> find(String stringSearch);
}
