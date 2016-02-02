package net.openblazar.bfp.services;

import net.openblazar.bfp.common.users.UserDetails;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface UserService {

	UserDetails getUserDetails(String userName);

	List<UserDetails> getUsers();

	boolean registerUser(String userName, String userMail, char[] password);

}
