package net.openblazar.bfp.services;

import com.google.inject.Inject;
import net.openblazar.bfp.common.users.UserDetails;
import net.openblazar.bfp.core.user.SecurityUtil;
import net.openblazar.bfp.core.user.SecurityUtilImpl;
import net.openblazar.bfp.core.user.enums.UserState;
import net.openblazar.bfp.database.dao.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
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
		return userDAO.findUser(userName);
	}

	@Override
	public List<UserDetails> getUsers() {
		return userDAO.findAllUsers();
	}

	@Override
	public boolean registerUser(String userName, String userMail, char[] password) {
		try {
			String currentTime = formatter.format(Instant.now());
			userDAO.saveUser(
					userName,
					userMail,
					securityUtil.hashPassword(password),
					UserState.ACTIVE.getState(),
					currentTime,
					currentTime);
		} catch (Exception e) {
			LOGGER.warn("Failed to register user " + userName + ". {}", e);
			return false;
		}
		return true;
	}

}
