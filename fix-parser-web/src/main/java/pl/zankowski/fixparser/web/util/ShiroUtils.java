package pl.zankowski.fixparser.web.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import pl.zankowski.fixparser.web.security.DatabaseUserRealm;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.user.api.UserId;

import java.util.Collection;

public class ShiroUtils {

    public UserDetailsTO getCurrentUserDetails() {
        return (UserDetailsTO) SecurityUtils.getSubject().getPrincipal();
    }

    public UserId getCurrentUserID() {
        UserDetailsTO userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getUserId();
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
