package net.openblazar.bfp.core.security.realm;

import com.google.inject.Inject;
import net.openblazar.bfp.data.user.Role;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.data.user.UserID;
import net.openblazar.bfp.database.dao.UserDAO;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class DatabaseUserRealm extends AuthorizingRealm {

    private final UserDAO userDAO;

    @Inject
    public DatabaseUserRealm(UserDAO userDAO, CredentialsMatcher credentialsMatcher) {
        setName("User Realm");
        setCredentialsMatcher(credentialsMatcher);
        this.userDAO = userDAO;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        UserDetails userDetails = userDAO.findUserByLogin(token.getUsername());
        if (userDetails != null) {
            return new SimpleAuthenticationInfo(userDetails, userDetails.getPassword(), getName());
        } else {
            throw new AuthenticationException("Failed to find user " + ((UsernamePasswordToken) authenticationToken).getUsername());
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        UserID userId = new UserID((Long) principals.fromRealm(getName()).iterator().next());
        UserDetails userDetails = userDAO.findUserById(userId);
        if (userDetails != null) {
            List<Role> userRoles = userDAO.findUserRoles(userId);
            SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
            for (Role role : userRoles) {
                authInfo.addRole(role.getName());
            }
            return authInfo;
        }
        return null;
    }

}
