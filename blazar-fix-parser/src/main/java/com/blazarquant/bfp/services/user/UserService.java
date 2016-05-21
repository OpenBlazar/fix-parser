package com.blazarquant.bfp.services.user;

import com.blazarquant.bfp.core.security.exception.DecodingException;
import com.blazarquant.bfp.core.user.UserSettingsCache;
import com.blazarquant.bfp.data.user.Role;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import org.apache.shiro.subject.Subject;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface UserService {

	UserDetails getUserDetails(String userName);

	List<UserDetails> getUsers();

	List<Role> getUserRoles(UserID userID);

	boolean isUserNameExists(String userName);

	boolean isUserMailExists(String userMail);

	boolean isUserActive(String userName);

	boolean registerUser(String userName, String userMail, char[] password);

	boolean confirmUser(String confirmationKey) throws DecodingException;

	Subject getCurrentUser();

	UserSettingsCache getUserSettingsCache();

}
