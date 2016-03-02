package net.openblazar.bfp.services.user;

import com.google.inject.Inject;
import net.openblazar.bfp.data.user.Role;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.data.user.UserID;
import net.openblazar.bfp.core.security.util.SecurityUtil;
import net.openblazar.bfp.data.user.UserState;
import net.openblazar.bfp.database.dao.UserDAO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
																		.withZone(ZoneId.systemDefault());

	private UserDAO userDAO;
	private SecurityUtil securityUtil;

	@Inject
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Inject
	public void setSecurityUtil(SecurityUtil securityUtil) {
		this.securityUtil = securityUtil;
	}

	@Override
	public UserDetails getUserDetails(String userName) {
		return userDAO.findUserByLogin(userName);
	}

	@Override
	public List<UserDetails> getUsers() {
		return userDAO.findAllUsers();
	}

	@Override
	public List<Role> getUserRoles(UserID userID) {
		return userDAO.findUserRoles(userID);
	}

	@Override
	public boolean isUserExists(String userName) {
		return userDAO.isUserExists(userName) == 1;
	}

	@Override
	public boolean registerUser(String userName, String userMail, char[] password) {
		String currentTime = formatter.format(Instant.now());
		userDAO.saveUser(userName, userMail, securityUtil.hashPassword(password),
				UserState.ACTIVE, currentTime, currentTime);
		return true;
	}

	@Override
	public Subject getCurrentUser() {
		return SecurityUtils.getSubject();
	}

}
