package pl.zankowski.fixparser.user;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import pl.zankowski.fixparser.mail.spi.MailService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.Role;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.api.UserSetting;
import pl.zankowski.fixparser.user.api.UserState;
import pl.zankowski.fixparser.user.entity.UserDetails;
import pl.zankowski.fixparser.user.spi.UserService;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static pl.zankowski.fixparser.core.framework.HashUtil.hash;
import static pl.zankowski.fixparser.core.framework.RandomUtil.generateRandomKey;

public class DefaultUserService implements UserService {

    private final UserDAO userDAO;
    private final MailService mailService;
    private final UserSettingsCache userSettingsCache;
    private final UserMapper userMapper;

    @Inject
    public DefaultUserService(final UserDAO userDAO, final MailService mailService, final UserMapper userMapper) {
        this.userDAO = userDAO;
        this.mailService = mailService;
        this.userSettingsCache = new UserSettingsCache(userDAO);
        this.userMapper = userMapper;
    }

    @Override
    public UserDetailsTO getUserDetails(String userName) {
        return userMapper.map(userDAO.findUserByLogin(userName));
    }

    @Override
    public UserDetailsTO getUserDetailsByMail(String userMail) {
        return userMapper.map(userDAO.findUserByMail(userMail));
    }

    @Override
    public List<UserDetailsTO> getUsers() {
        return userDAO.findAllUsers().stream()
                .map(userMapper::map)
                .collect(toList());
    }

    @Override
    public List<Role> getUserRoles(UserId userId) {
        return userDAO.findUserRoles(userId);
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
    public void addUserPermission(UserId userId, Permission permission) {
        userDAO.saveUserPermission(userId, permission.name());
    }

    @Override
    public boolean registerUser(String userName, String userMail, char[] password) {
        // Save user
        Instant currentTime = Instant.now();
        userDAO.saveUser(userName, userMail, hash(password),
                UserState.INACTIVE, currentTime, currentTime);
        UserId userId = userDAO.findUserIDByLogin(userName);
        userDAO.saveUserRole(userId, Role.USER_ROLE);
        userDAO.saveUserPermission(userId, Permission.BASIC.name());

        // Generate confirmation key
        String confirmationKey = generateRandomKey();
        userDAO.updateConfirmationKey(userId, confirmationKey);

        // Send to mail Service
        mailService.sendConfirmationLink(confirmationKey, userMail);
        return true;
    }

    @Override
    public boolean confirmUser(String confirmationKey) {
        final UserDetails userDetails = userDAO.findUserByConfirmationKey(confirmationKey);
        if (userDetails == null) {
            return false;
        }
        getUserSettingsCache().createDefaultParameters(userDetails.getUserId());
        userDAO.updateUserStatus(userDetails.getUserId(), UserState.ACTIVE);
        return true;
    }

    @Override
    public void loginUser(UserId userId) {
        userDAO.updateLastLogin(userId, Instant.now());
    }

    //    @Override
    public UserSettingsCache getUserSettingsCache() {
        return userSettingsCache;
    }

    @Override
    public Object getParameter(final UserId userId, final UserSetting userSetting) {
        return getUserSettingsCache().getObject(userId, UserSetting.DEFAULT_PROVIDER);
    }

    @Override
    public void setParameter(final UserId userId, final UserSetting userSetting, final Object parameter) {
        getUserSettingsCache().setParameter(userId, UserSetting.DEFAULT_PROVIDER, parameter);
    }

    @Override
    public List<String> findUserPermissions(final UserId userId) {
        return Lists.newArrayList();
    }
}
