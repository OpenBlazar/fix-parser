package net.openblazar.bfp.services;

import net.openblazar.bfp.data.user.Role;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.data.user.UserID;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface UserService {

	UserDetails getUserDetails(String userName);

	List<UserDetails> getUsers();

	List<Role> getUserRoles(UserID userID);

	boolean isUserExists(String userName);

	boolean registerUser(String userName, String userMail, char[] password);

}
