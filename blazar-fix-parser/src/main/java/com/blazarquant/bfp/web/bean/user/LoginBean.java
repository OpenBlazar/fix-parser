package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.BlazarURL;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "loginBean")
@ApplicationScoped
public class LoginBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);

    private String username;
    private String password;
    private Boolean rememberMe;

    @PostConstruct
    public void init() {
        super.init();
    }

    public void doLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                currentUser.login(token);

                FacesContext.getCurrentInstance().getExternalContext().redirect(BlazarURL.HOME_URL);
            }
        } catch (Exception e) {
            facesError("Please check the information you entered and try again.", e);
        } finally {
            token.clear();
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
