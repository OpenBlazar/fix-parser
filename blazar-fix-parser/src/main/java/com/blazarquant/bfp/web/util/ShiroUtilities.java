package com.blazarquant.bfp.web.util;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import org.apache.shiro.SecurityUtils;

/**
 * @author Wojciech Zankowski
 */
public class ShiroUtilities {

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

}
