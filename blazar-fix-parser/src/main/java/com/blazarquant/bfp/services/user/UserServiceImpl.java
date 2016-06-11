package com.blazarquant.bfp.services.user;

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

import java.time.Instant;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private SecurityUtil securityUtil;
    private MailService mailService;
    private UserSettingsCache userSettingsCache;

    @Inject
    public UserServiceImpl(UserDAO userDAO, SecurityUtil securityUtil, MailService mailService) {
        this.userDAO = userDAO;
        this.securityUtil = securityUtil;
        this.mailService = mailService;
        this.userSettingsCache = new UserSettingsCache(userDAO);
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
        UserID userID = userDAO.findUserIDByLogin(userName);
        userDAO.saveUserRole(userID, Role.USER_ROLE);
        userDAO.saveUserPermission(userID, Permission.BASIC.name());

        // Generate confirmation key
        String confirmationKey = securityUtil.generateConfirmationKey(userID.getId(), userName, userMail);
        userDAO.updateConfirmationKey(userID, confirmationKey);

        // Send to mail Service
        mailService.sendConfirmationLink(confirmationKey, userMail);
        return true;
    }

    @Override
    public boolean confirmUser(String confirmationKey) throws DecodingException {
        UserID userID = new UserID(securityUtil.decodeConfirmationKey(confirmationKey));

        String storedKey = userDAO.findConfirmationKeyFromUser(userID);
        if (confirmationKey.equals(storedKey)) {
            userDAO.updateUserStatus(userID, UserState.ACTIVE);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserSettingsCache getUserSettingsCache() {
        return userSettingsCache;
    }

}
