package net.openblazar.bfp.web.bean.user;

import net.openblazar.bfp.services.user.UserService;
import net.openblazar.bfp.web.bean.AbstractBean;
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
