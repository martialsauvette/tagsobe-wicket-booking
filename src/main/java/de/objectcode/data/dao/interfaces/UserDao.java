package de.objectcode.data.dao.interfaces;

import de.objectcode.data.dataobjects.User;


public interface UserDao extends Dao<User> {

	public boolean authenticate(final String userName, final String password);
	
	public User findUserByUserNameAndPassword (final String userName, final String password);
	
	public User findUserByUserName(final String userName);
	
	public boolean userAlreadyExist(final String userName);	
	
	public void createUser(final User user);
}
