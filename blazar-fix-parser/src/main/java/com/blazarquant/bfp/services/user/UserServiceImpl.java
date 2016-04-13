package com.blazarquant.bfp.services.user;

import com.blazarquant.bfp.core.security.exception.DecodingException;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.data.user.Role;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.data.user.UserState;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.blazarquant.bfp.services.mail.MailService;
import com.google.inject.Inject;
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
    private MailService mailService;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setSecurityUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Inject
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
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
    public boolean isUserActive(String userName) {
        return UserState.getUserStateFromCode(userDAO.isUserActive(userName)) == UserState.ACTIVE;
    }

    @Override
    public boolean registerUser(String userName, String userMail, char[] password) {
        // Save user
        String currentTime = formatter.format(Instant.now());
        userDAO.saveUser(userName, userMail, securityUtil.hashPassword(password),
                UserState.INACTIVE, currentTime, currentTime);
        Long userID = userDAO.findUserIDByLogin(userName);

        // Generate confirmation key
        String confirmationKey = securityUtil.generateConfirmationKey(userID, userName, userMail);
        userDAO.updateConfirmationKey(userID, confirmationKey);

        // Send to mail Service
        mailService.sendConfirmationLink(confirmationKey, userMail);
        return true;
    }

    @Override
    public boolean confirmUser(String confirmationKey) throws DecodingException {
        long userID = securityUtil.decodeConfirmationKey(confirmationKey);

        String storedKey = userDAO.findConfirmationKeyFromUser(userID);
        if (confirmationKey.equals(storedKey)) {
            userDAO.updateUserStatus(userID, UserState.ACTIVE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Subject getCurrentUser() {
        return SecurityUtils.getSubject();
    }

}
