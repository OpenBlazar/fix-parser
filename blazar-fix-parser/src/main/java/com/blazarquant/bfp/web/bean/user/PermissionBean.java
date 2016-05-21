package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "currentUser")
@SessionScoped
public class PermissionBean extends AbstractBean {

    private ShiroUtilities shiroUtilities;

    @PostConstruct
    public void init() {
        super.init();
    }

    @Inject
    public void setShiroUtilities(ShiroUtilities shiroUtilities) {
        this.shiroUtilities = shiroUtilities;
    }

    public boolean isAuthenticated() {
        return shiroUtilities.isUserAuthenticated();
    }

    public boolean isRemembered() {
        return shiroUtilities.isUserRemembered();
    }

    public String getUserName() {
        UserDetails userDetails = shiroUtilities.getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getUserName();
        } else {
            return "";
        }
    }

}
