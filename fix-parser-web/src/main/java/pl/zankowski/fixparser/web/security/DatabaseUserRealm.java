package pl.zankowski.fixparser.web.security;

import pl.zankowski.bfp.data.user.Role;
import pl.zankowski.bfp.data.user.UserDetails;
import pl.zankowski.bfp.database.dao.UserDAO;
import com.google.inject.Inject;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import pl.zankowski.fixparser.user.api.Role;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.spi.UserService;

import java.util.List;

public class DatabaseUserRealm extends AuthorizingRealm {

    public static final String REALM_NAME = "User Realm";

    private final UserService userService;

    @Inject
    public DatabaseUserRealm(UserService userService, CredentialsMatcher credentialsMatcher) {
        setName(REALM_NAME);
        setCredentialsMatcher(credentialsMatcher);
        this.userService = userService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserDetailsTO userDetails = userService.getUserDetails(token.getUsername());
        if (userDetails != null) {
            return new SimpleAuthenticationInfo(userDetails, userDetails.getPassword(), getName());
        } else {
            throw new AuthenticationException("Failed to find user " + ((UsernamePasswordToken) authenticationToken).getUsername());
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserDetailsTO userDetails = (UserDetailsTO) principals.fromRealm(getName()).iterator().next();
        if (userDetails != null) {
            List<Role> userRoles = userService.getUserRoles(userDetails.getUserId());
            SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
            userRoles.forEach(r -> authInfo.addRole(r.getName()));
            List<String> userPermissions = userService.findUserPermissions(userDetails.getUserId());
            userPermissions.forEach(p -> authInfo.addStringPermission(p));
            return authInfo;
        }
        return null;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
}
