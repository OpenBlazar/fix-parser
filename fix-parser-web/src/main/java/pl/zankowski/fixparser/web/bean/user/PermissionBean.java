package pl.zankowski.fixparser.web.bean.user;

import com.google.inject.Inject;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.UserDetailsTO;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

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
        UserDetailsTO userDetails = shiroUtils.getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getUserName();
        } else {
            return "";
        }
    }

}
