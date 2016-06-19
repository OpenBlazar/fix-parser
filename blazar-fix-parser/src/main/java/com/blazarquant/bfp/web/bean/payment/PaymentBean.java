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
package com.blazarquant.bfp.web.bean.payment;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.services.payment.PaymentService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean
@ViewScoped
public class PaymentBean extends AbstractBean {

    private PaymentService paymentService;
    private UserService userService;
    private ShiroUtils shiroUtils;

    private String paymentToken;

    @PostConstruct
    @Override
    public void init() {
        super.init();
    }

    @Inject
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    public void doConfirmPayment() throws PaymentException {
        SubscriptionPlan subscriptionPlan = paymentService.confirmPayment(paymentToken);
        if (subscriptionPlan != null && shiroUtils.isUserAuthenticated()) {
            UserID userID = shiroUtils.getCurrentUserID();
            Permission permission = Permission.valueOf(subscriptionPlan.name());
            switch (permission) {
                case ENTERPRISE:
                    userService.addUserPermission(userID, Permission.ENTERPRISE);
                    userService.addUserPermission(userID, Permission.PRO);
                    break;
                case PRO:
                    userService.addUserPermission(userID, Permission.PRO);
                    break;
                default:
                    break;
            }
            shiroUtils.clearCachedAuthorizationInfo();
        }
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }
}
