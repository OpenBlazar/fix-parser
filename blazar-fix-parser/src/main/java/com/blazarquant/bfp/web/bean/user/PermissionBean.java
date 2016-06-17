package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "currentUser")
@SessionScoped
public class PermissionBean extends AbstractBean {

    private ShiroUtils shiroUtils;

    @PostConstruct
    public void init() {
        super.init();
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    public boolean isAuthenticated() {
        return shiroUtils.isUserAuthenticated();
    }

    public boolean isRemembered() {
        return shiroUtils.isUserRemembered();
    }

    public boolean isProOrEnterprise() {
        return shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name());
    }

    public String getUserName() {
        UserDetails userDetails = shiroUtils.getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getUserName();
        } else {
            return "";
        }
    }

}
