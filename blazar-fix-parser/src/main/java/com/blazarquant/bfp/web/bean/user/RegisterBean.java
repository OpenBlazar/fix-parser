/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.web.bean.user;

import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.FacesUtils;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "registerBean")
@RequestScoped
public class RegisterBean extends AbstractBean {

    public static final String USERNAME_EXISTS = "User with given name %s already exists.";
    public static final String USERMAIL_EXISTS = "User with given mail %s already exists.";
    public static final String REGISTER_SUCCESS = "Congratulations, registration has been successful. Please check your email to confirm registration.";
    public static final String FAILED_TO_REGISTER = "Failed to register user.";
    public static final String PASSWORD_MISMATCH = "Password mismatch.";

    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterBean.class);

    private UserService userService;
    private FacesUtils facesUtils;

    private String username;
    private String email;
    private String password;
    private String passwordConfirm;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
            facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, PASSWORD_MISMATCH);
            return;
        }

        try {
            if (userService.isUserNameExists(getUsername())) {
                facesUtils.addMessage(FacesMessage.SEVERITY_WARN, String.format(USERNAME_EXISTS, getUsername()));
                return;
            }
            if (userService.isUserMailExists(getEmail())) {
                facesUtils.addMessage(FacesMessage.SEVERITY_WARN, String.format(USERMAIL_EXISTS, getEmail()));
                return;
            }
            userService.registerUser(getUsername(), getEmail(), getPassword().toCharArray());
            facesUtils.addMessage(FacesMessage.SEVERITY_INFO, REGISTER_SUCCESS);
        } catch (Exception e) {
            facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, FAILED_TO_REGISTER);
            LOGGER.error(FAILED_TO_REGISTER, e);
        }
    }

}
