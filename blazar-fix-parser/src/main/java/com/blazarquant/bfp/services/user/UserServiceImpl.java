package com.blazarquant.bfp.services.user;

import com.blazarquant.bfp.core.security.config.DatabaseUserRealm;
import com.blazarquant.bfp.core.security.exception.DecodingException;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.user.UserSettingsCache;
import com.blazarquant.bfp.data.user.*;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.blazarquant.bfp.services.mail.MailService;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.crypto.Data;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDAO userDAO;
    private SecurityUtil securityUtil;
    private MailService mailService;
    private UserSettingsCache userSettingsCache;

    @Inject
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
        userSettingsCache = new UserSettingsCache(userDAO);
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
    public UserDetails getUserDetailsByMail(String userMail) {
        return userDAO.findUserByMail(userMail);
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
    public boolean isUserNameExists(String userName) {
        return userDAO.isUserNameExists(userName) == 1;
    }

    @Override
    public boolean isUserMailExists(String userMail) {
        return userDAO.isUserMailExists(userMail) == 1;
    }

    @Override
    public boolean isUserActive(String userName) {
        return UserState.getUserStateFromCode(userDAO.isUserActive(userName)) == UserState.ACTIVE;
    }

    @Override
    public void addUserPermission(UserID userID, Permission permission) {
        userDAO.saveUserPermission(userID, permission.name());
    }

    @Override
    public boolean registerUser(String userName, String userMail, char[] password) {
        // Save user
        Instant currentTime = Instant.now();
        userDAO.saveUser(userName, userMail, securityUtil.hashPassword(password),
                UserState.INACTIVE, currentTime, currentTime);
        Long userID = userDAO.findUserIDByLogin(userName);
        UserID userIDObject = new UserID(userID);
        userDAO.saveUserRole(userIDObject, Role.USER_ROLE);
        userDAO.saveUserPermission(userIDObject, Permission.BASIC.name());

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

    @Override
    public UserSettingsCache getUserSettingsCache() {
        return userSettingsCache;
    }

}
