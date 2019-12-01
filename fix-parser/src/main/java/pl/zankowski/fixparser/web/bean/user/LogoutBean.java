package pl.zankowski.fixparser.web.bean.user;

import pl.zankowski.fixparser.web.bean.AbstractBean;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class LogoutBean extends AbstractBean {
//
//    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutBean.class);
//
//    private ShiroUtils shiroUtils;
//    private FacesUtils facesUtils;
//
//    @PostConstruct
//    @Override
//    public void init() {
//        super.init();
//    }
//
//    @Inject
//    public void setShiroUtils(ShiroUtils shiroUtils) {
//        this.shiroUtils = shiroUtils;
//    }
//
//    @Inject
//    public void setFacesUtils(FacesUtils facesUtils) {
//        this.facesUtils = facesUtils;
//    }
//
//    public void doLogout() {
//        Subject currentUser = shiroUtils.getSubject();
//        try {
//            if (currentUser.isAuthenticated()) {
//                currentUser.logout();
//
//                facesUtils.redirect(BlazarURL.HOME_URL);
//            }
//        } catch (Exception e) {
//            LOGGER.warn("Failed to logout user. {}", e);
//        }
//    }

}
