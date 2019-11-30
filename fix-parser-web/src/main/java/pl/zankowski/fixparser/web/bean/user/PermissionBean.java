package pl.zankowski.fixparser.web.bean.user;

import pl.zankowski.fixparser.web.bean.AbstractBean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("currentUser")
@SessionScoped
public class PermissionBean extends AbstractBean {

//    private ShiroUtils shiroUtils;
//
//    @PostConstruct
//    public void init() {
//        super.init();
//    }
//
//    @Inject
//    public void setShiroUtils(ShiroUtils shiroUtils) {
//        this.shiroUtils = shiroUtils;
//    }
//
//    public boolean isAuthenticated() {
//        return shiroUtils.isUserAuthenticated();
//    }
//
//    public boolean isRemembered() {
//        return shiroUtils.isUserRemembered();
//    }
//
//    public boolean isProOrEnterprise() {
//        return shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name());
//    }
//
//    public String getUserName() {
//        UserDetailsTO userDetails = shiroUtils.getCurrentUserDetails();
//        if (userDetails != null) {
//            return userDetails.getUserName();
//        } else {
//            return "";
//        }
//    }

}
