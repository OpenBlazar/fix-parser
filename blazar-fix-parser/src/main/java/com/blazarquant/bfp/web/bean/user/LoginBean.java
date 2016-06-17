package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.BlazarURL;
import com.blazarquant.bfp.web.util.FacesUtilities;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean extends AbstractBean {

    public static final String ACCOUNT_NOT_ACTIVE = "Your account is not active. Please confirm your registration.";
    public static final String LOGIN_FAILED = "Please check the information you entered and try again.";
    public static final String FAILED_TO_REDIRECT = "Failed to redirect to home page.";

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);

    private UserService userService;
    private ParserService parserService;
    private ShiroUtilities shiroUtilities;
    private FacesUtilities facesUtilities;

    private String username;
    private String password;
    private Boolean rememberMe = Boolean.TRUE;

    @PostConstruct
    public void init() {
        super.init();
        if (shiroUtilities.isUserAuthenticated()) {
            try {
                redirectToPreviousPage();
            } catch (IOException e) {
                facesUtilities.addMessage(FacesMessage.SEVERITY_ERROR, FAILED_TO_REDIRECT);
                LOGGER.error(FAILED_TO_REDIRECT, e);
            }
        }
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @Inject
    public void setShiroUtilities(ShiroUtilities shiroUtilities) {
        this.shiroUtilities = shiroUtilities;
    }

    @Inject
    public void setFacesUtilities(FacesUtilities facesUtilities) {
        this.facesUtilities = facesUtilities;
    }

    public void doLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        try {
            if (!userService.isUserActive(getUsername())) {
                facesUtilities.addMessage(FacesMessage.SEVERITY_ERROR, ACCOUNT_NOT_ACTIVE);
                return;
            }

            Subject currentUser = shiroUtilities.getSubject();
            if (!currentUser.isAuthenticated()) {
                currentUser.login(token);

                UserID userID = ((UserDetails) currentUser.getPrincipal()).getUserID();
                parserService.loadProvidersForUser(userID);
                userService.getUserSettingsCache().loadParameters(userID);

                redirectToPreviousPage();
            } else {
                redirectToPreviousPage();
            }
        } catch (Exception e) {
            facesUtilities.addMessage(FacesMessage.SEVERITY_ERROR, LOGIN_FAILED);
            LOGGER.error(LOGIN_FAILED, e);
        } finally {
            token.clear();
        }
    }

    private void redirectToPreviousPage() throws IOException {
        // TODO fix and move to filter?
        facesUtilities.redirect(BlazarURL.PARSER_URL);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
