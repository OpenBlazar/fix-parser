package net.openblazar.bfp.services;

import com.google.inject.Inject;
import net.openblazar.bfp.common.users.UserDetails;
import net.openblazar.bfp.database.dao.UserDAO;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class UserServiceImpl implements UserService {

	private UserDAO userDAO;

	@Inject
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public UserDetails getUserDetails(String userName) {
		return userDAO.findUser(userName);
	}

	public List<UserDetails> getUsers() {
		return userDAO.findAllUsers();
	}

}
