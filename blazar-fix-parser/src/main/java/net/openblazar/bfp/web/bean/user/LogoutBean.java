package net.openblazar.bfp.web.bean.user;

import net.openblazar.bfp.web.bean.AbstractBean;
import net.openblazar.bfp.web.util.BlazarURL;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean
@RequestScoped
public class LogoutBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutBean.class);

    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            if(currentUser.isAuthenticated()) {
                currentUser.logout();

                FacesContext.getCurrentInstance().getExternalContext().redirect(BlazarURL.HOME_URL);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to logout user. {}", e);
        }
    }

}
