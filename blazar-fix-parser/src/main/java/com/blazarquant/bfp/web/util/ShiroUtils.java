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
package com.blazarquant.bfp.web.util;

import com.blazarquant.bfp.core.security.config.DatabaseUserRealm;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import java.util.Collection;

/**
 * @author Wojciech Zankowski
 */
public class ShiroUtils {

    public UserDetails getCurrentUserDetails() {
        return (UserDetails) SecurityUtils.getSubject().getPrincipal();
    }

    public UserID getCurrentUserID() {
        UserDetails userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getUserID();
        }
        return null;
    }

    public boolean isUserAuthenticated() {
        return SecurityUtils.getSubject().isAuthenticated();
    }

    public boolean isUserRemembered() {
        return SecurityUtils.getSubject().isRemembered();
    }

    public boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    public boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public void clearCachedAuthorizationInfo() {
        Collection<Realm> realms = ((DefaultWebSecurityManager) SecurityUtils.getSecurityManager()).getRealms();
        for (Realm realm : realms) {
            if (realm instanceof DatabaseUserRealm) {
                ((DatabaseUserRealm) realm).clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
            }
        }
    }

}
