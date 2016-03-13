package net.openblazar.bfp.web.bean.user;

import net.openblazar.bfp.web.bean.AbstractBean;
import net.openblazar.bfp.web.util.BlazarURL;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.IOException;

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
        } catch (UnknownAccountException e) {
            facesError("Unknown account.", e);
        } catch (IncorrectCredentialsException e) {
            facesError("Wrong password.", e);
        } catch (LockedAccountException e) {
            facesError("Locked account.", e);
        } catch (AuthenticationException e) {
            facesError("Unknown error.", e);
        } catch (IOException e) {
            facesError("Failed to load page.", e);
        } catch (Exception e) {
            facesError("Whatever", e);
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
