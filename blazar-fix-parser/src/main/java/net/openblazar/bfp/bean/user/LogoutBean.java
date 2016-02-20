package net.openblazar.bfp.bean.user;

import net.openblazar.bfp.bean.AbstractBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean
@SessionScoped
public class LogoutBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutBean.class);

    public void doLogout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (Exception e) {
            LOGGER.warn("Failed to logout user. {}", e);
        }
    }

}
