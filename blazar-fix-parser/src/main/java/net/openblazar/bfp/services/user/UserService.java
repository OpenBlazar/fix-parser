package net.openblazar.bfp.services.user;

import net.openblazar.bfp.data.user.Role;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.data.user.UserID;
import org.apache.shiro.subject.Subject;

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

	Subject getCurrentUser();

}
