package pl.zankowski.fixparser.user.spi;

import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.Role;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserId;

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

}
