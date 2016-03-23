package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.services.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "currentUser")
@RequestScoped
public class PermissionBean extends AbstractBean {

    private UserService userService;

    @PostConstruct
    public void init() {
        super.init();
    }

    public boolean isAuthenticated() {
        Subject user = SecurityUtils.getSubject();
        return user.isAuthenticated();
    }

}
