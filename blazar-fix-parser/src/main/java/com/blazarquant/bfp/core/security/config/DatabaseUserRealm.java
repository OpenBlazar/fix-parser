/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.core.security.config;

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

    public static final String REALM_NAME = "User Realm";

    private final UserDAO userDAO;

    @Inject
    public DatabaseUserRealm(UserDAO userDAO, CredentialsMatcher credentialsMatcher) {
        setName(REALM_NAME);
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
