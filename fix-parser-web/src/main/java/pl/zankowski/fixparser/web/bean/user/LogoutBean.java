package pl.zankowski.fixparser.web.bean.user;

import com.google.inject.Inject;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.BlazarURL;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class LogoutBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutBean.class);

    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    public void doLogout() {
        Subject currentUser = shiroUtils.getSubject();
        try {
            if (currentUser.isAuthenticated()) {
                currentUser.logout();

                facesUtils.redirect(BlazarURL.HOME_URL);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to logout user. {}", e);
        }
    }

}
