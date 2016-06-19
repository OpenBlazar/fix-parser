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

import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.services.parser.ParserService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.BlazarURL;
import com.blazarquant.bfp.web.util.FacesUtils;
import com.blazarquant.bfp.web.util.ShiroUtils;
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
    private ShiroUtils shiroUtils;
    private FacesUtils facesUtils;

    private String username;
    private String password;
    private Boolean rememberMe = Boolean.TRUE;

    @PostConstruct
    public void init() {
        super.init();
        if (shiroUtils.isUserAuthenticated()) {
            try {
                redirectToPreviousPage();
            } catch (IOException e) {
                facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, FAILED_TO_REDIRECT);
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
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    public void doLogin() {
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        try {
            if (!userService.isUserActive(getUsername())) {
                facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, ACCOUNT_NOT_ACTIVE);
                return;
            }

            Subject currentUser = shiroUtils.getSubject();
            if (!currentUser.isAuthenticated()) {
                currentUser.login(token);

                UserID userID = ((UserDetails) currentUser.getPrincipal()).getUserID();
                parserService.loadProvidersForUser(userID);
                userService.getUserSettingsCache().loadParameters(userID);
                userService.loginUser(userID);

                redirectToPreviousPage();
            } else {
                redirectToPreviousPage();
            }
        } catch (Exception e) {
            facesUtils.addMessage(FacesMessage.SEVERITY_ERROR, LOGIN_FAILED);
            LOGGER.error(LOGIN_FAILED, e);
        } finally {
            token.clear();
        }
    }

    private void redirectToPreviousPage() throws IOException {
        // TODO fix and move to filter?
        facesUtils.redirect(BlazarURL.PARSER_URL);
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
