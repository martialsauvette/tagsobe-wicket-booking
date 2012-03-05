package de.objectcode.data.dao.interfaces;

import de.objectcode.data.dataobjects.User;
/**
 * @author Richard Wilkinson - richard.wilkinson@jweekend.com
 *
 */
public interface UserDao extends Dao<User> {

	public boolean authenticate(final String userName, final String password);
	
	public User findUserByUserNameAndPassword (final String userName, final String password);
}
