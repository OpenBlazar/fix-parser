package net.openblazar.bfp.bean.user;

import net.openblazar.bfp.bean.AbstractBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends AbstractBean {

    public static final String HOME_URL = "/";

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginBean.class);

    private String username;
    private String password;
    private Boolean rememberMe;

    public void doLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        token.setRememberMe(true);
        try {
            Subject currentUser = SecurityUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                currentUser.login(token);

                FacesContext.getCurrentInstance().getExternalContext().redirect(HOME_URL);
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
        } finally {
            token.clear();
        }
    }

    public boolean isAuthenticated() {
        Subject user = SecurityUtils.getSubject();
        return user.isAuthenticated();
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
