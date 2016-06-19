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

import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean(name = "currentUser")
@SessionScoped
public class PermissionBean extends AbstractBean {

    private ShiroUtils shiroUtils;

    @PostConstruct
    public void init() {
        super.init();
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    public boolean isAuthenticated() {
        return shiroUtils.isUserAuthenticated();
    }

    public boolean isRemembered() {
        return shiroUtils.isUserRemembered();
    }

    public boolean isProOrEnterprise() {
        return shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name());
    }

    public String getUserName() {
        UserDetails userDetails = shiroUtils.getCurrentUserDetails();
        if (userDetails != null) {
            return userDetails.getUserName();
        } else {
            return "";
        }
    }

}
