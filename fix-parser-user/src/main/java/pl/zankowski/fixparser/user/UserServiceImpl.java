package pl.zankowski.fixparser.user;


import com.google.inject.Inject;
import pl.zankowski.fixparser.user.entity.Permission;
import pl.zankowski.fixparser.user.entity.Role;
import pl.zankowski.fixparser.user.entity.UserDetails;
import pl.zankowski.fixparser.user.entity.UserID;
import pl.zankowski.fixparser.user.entity.UserState;

import java.time.Instant;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private MailService mailService;
    private UserSettingsCache userSettingsCache;

    @Inject
    public UserServiceImpl(final UserDAO userDAO, final MailService mailService) {
        this.userDAO = userDAO;
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
    public boolean confirmUser(String confirmationKey) {
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
    public void loginUser(UserID userID) {
        userDAO.updateLastLogin(userID, Instant.now());
    }

    @Override
    public UserSettingsCache getUserSettingsCache() {
        return userSettingsCache;
    }

}
