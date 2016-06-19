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

import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.data.user.UserID;
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
@ManagedBean(name = "confirmationBean")
@RequestScoped
public class ConfirmationBean extends AbstractBean {

    public static final String FAILED_TO_CONFIRM = "Failed to confirm registration. Invalid confirmation key.";
    public static final String CONFIRMATION_SUCCEDED = "Congratulations, registration has been successful. Your account is active right now. Please sign in.";

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationBean.class);

    private UserService userService;
    private SecurityUtil securityUtil;
    private FacesUtils facesUtils;

    private String confirmationKey;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setSecurityUtil(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
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
                facesUtils.addMessage(FacesMessage.SEVERITY_INFO, CONFIRMATION_SUCCEDED);

                UserID userID = new UserID(securityUtil.decodeConfirmationKey(confirmationKey));
                userService.getUserSettingsCache().createDefaultParameters(userID);
            } else {
                facesUtils.addMessage(FacesMessage.SEVERITY_WARN, FAILED_TO_CONFIRM);
            }
        } catch (Exception e) {
            facesUtils.addMessage(FacesMessage.SEVERITY_WARN, FAILED_TO_CONFIRM);
            LOGGER.error(FAILED_TO_CONFIRM, e);
        }
    }

}
