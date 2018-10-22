package pl.zankowski.fixparser.user;

import pl.zankowski.fixparser.user.entity.Permission;
import pl.zankowski.fixparser.user.entity.Role;
import pl.zankowski.fixparser.user.entity.UserDetails;
import pl.zankowski.fixparser.user.entity.UserID;

import java.util.List;

public interface UserService {

    UserDetails getUserDetails(String userName);

    UserDetails getUserDetailsByMail(String userMail);

    List<UserDetails> getUsers();

    List<Role> getUserRoles(UserID userID);

    void addUserPermission(UserID userID, Permission permission);

    boolean isUserNameExists(String userName);

    boolean isUserMailExists(String userMail);

    boolean isUserActive(String userName);

    boolean registerUser(String userName, String userMail, char[] password);

    boolean confirmUser(String confirmationKey);

    void loginUser(UserID userID);

    UserSettingsCache getUserSettingsCache();

}
