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
