package com.blazarquant.bfp.core.security.config;

import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.Role;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.google.inject.Inject;
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
        UserDetails userDetails = (UserDetails) principals.fromRealm(getName()).iterator().next();
        if (userDetails != null) {
            List<Role> userRoles = userDAO.findUserRoles(userDetails.getUserID());
            SimpleAuthorizationInfo authInfo = new SimpleAuthorizationInfo();
            userRoles.forEach(r -> authInfo.addRole(r.getName()));
            List<String> userPermissions = userDAO.findUserPermissions(userDetails.getUserID());
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
