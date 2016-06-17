package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.BlazarURL;
import com.blazarquant.bfp.web.util.FacesUtilities;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean
@RequestScoped
public class LogoutBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(LogoutBean.class);

    private ShiroUtilities shiroUtilities;
    private FacesUtilities facesUtilities;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Inject
    public void setShiroUtilities(ShiroUtilities shiroUtilities) {
        this.shiroUtilities = shiroUtilities;
    }

    @Inject
    public void setFacesUtilities(FacesUtilities facesUtilities) {
        this.facesUtilities = facesUtilities;
    }

    public void doLogout() {
        Subject currentUser = shiroUtilities.getSubject();
        try {
            if (currentUser.isAuthenticated()) {
                currentUser.logout();

                facesUtilities.redirect(BlazarURL.HOME_URL);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to logout user. {}", e);
        }
    }

}
