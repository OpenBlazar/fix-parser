package pl.zankowski.fixparser.user.spi;

import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.Role;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.api.UserSetting;

import java.util.List;

public interface UserService {

    UserDetailsTO getUserDetails(String userName);

    UserDetailsTO getUserDetailsByMail(String userMail);

    List<UserDetailsTO> getUsers();

    List<Role> getUserRoles(UserId userId);

    void addUserPermission(UserId userId, Permission permission);

    boolean isUserNameExists(String userName);

    boolean isUserMailExists(String userMail);

    boolean isUserActive(String userName);

    boolean registerUser(String userName, String userMail, char[] password);

    boolean confirmUser(String confirmationKey);

    void loginUser(UserId userId);

    Object getParameter(UserId userId, UserSetting userSetting);

    void setParameter(UserId userId, UserSetting userSetting, Object parameter);

    List<String> findUserPermissions(UserId userId);
}
