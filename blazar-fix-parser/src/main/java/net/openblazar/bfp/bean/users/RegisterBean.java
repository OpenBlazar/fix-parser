package net.openblazar.bfp.bean.users;

import net.openblazar.bfp.bean.AbstractBean;
import net.openblazar.bfp.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "registerBean")
@ViewScoped
public class RegisterBean extends AbstractBean {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterBean.class);

    private UserService userService;

    private String username;
    private String password;
    private String passwordConfirm;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    @Override
    public void init() {
        super.init();
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

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void doRegister() {
        if (!getPassword().equals(getPasswordConfirm())) {
            facesError("Password mismatch.", new Exception(""));
            return;
        }

        try {
            if (userService.isUserExists(getUsername())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "User with given name " + getUsername() + " already exists.", ""));
                return;
            }
            userService.registerUser(getUsername(), "", getPassword().toCharArray());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Congratulations, registration has been successful", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Failed to register user.", ""));
        }
    }

    private void facesError(String message, Exception exception) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        LOGGER.error(message, exception);
    }

}