package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.BlazarURL;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "loginBean")
@RequestScoped
public class LoginBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);

    private UserService userService;
    private ParserService parserService;

    private String username;
    private String password;
    private Boolean rememberMe;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setParserService(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostConstruct
    public void init() {
        super.init();
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated()) {
            try {
                redirectToPreviousPage();
            } catch (IOException e) {
                facesError("Failed to redirect to home page.", e);
            }
        }
    }

    public void doLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        try {
            if (!userService.isUserActive(getUsername())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Your account is not active. Please confirm your registration.", null));
                return;
            }

            Subject currentUser = SecurityUtils.getSubject();
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
            facesError("Please check the information you entered and try again.", e);
        } finally {
            token.clear();
        }
    }

    private void redirectToPreviousPage() throws IOException {
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("originalURL")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect(
                    (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("originalURL"));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect(BlazarURL.PARSER_URL);
        }
    }

    protected void facesError(String message, Exception exception) {
        super.facesError(message, exception);
        LOGGER.error(message, exception);
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
