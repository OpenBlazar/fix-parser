package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.core.security.exception.DecodingException;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "confirmationBean")
@RequestScoped
public class ConfirmationBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConfirmationBean.class);

    private UserService userService;
    private SecurityUtil securityUtil;
    private String confirmationKey;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setSecurityUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    public String getConfirmationKey() {
        return confirmationKey;
    }

    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    public void doConfirmation() {
        try {
            boolean confirmed = userService.confirmUser(confirmationKey);
            if (confirmed) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Congratulations, registration has been successful. Your account is active right now. Please sign in.", ""));

                UserID userID = new UserID(securityUtil.decodeConfirmationKey(confirmationKey));
                userService.getUserSettingsCache().createDefaultParameters(userID);
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed to confirm registration. Invalid confirmation key.", ""));
            }
        } catch (DecodingException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed to confirm registration. Invalid confirmation key.", ""));
            LOGGER.error("Failed to confirm registration. Invalid confirmation key.", e);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed to confirm registration. Invalid confirmation key.", ""));
            LOGGER.error("Failed to confirm registration. Invalid confirmation key.", e);
        }
    }

}
