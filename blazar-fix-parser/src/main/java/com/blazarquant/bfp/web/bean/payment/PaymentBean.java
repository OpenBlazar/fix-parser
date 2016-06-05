package com.blazarquant.bfp.web.bean.payment;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.core.security.config.DatabaseUserRealm;
import com.blazarquant.bfp.data.user.Permission;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.services.payment.PaymentService;
import com.blazarquant.bfp.services.user.UserService;
import com.blazarquant.bfp.web.bean.AbstractBean;
import com.blazarquant.bfp.web.util.ShiroUtilities;
import com.google.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.Collection;

/**
 * @author Wojciech Zankowski
 */
@ManagedBean
@ViewScoped
public class PaymentBean extends AbstractBean {

    private PaymentService paymentService;
    private UserService userService;
    private ShiroUtilities shiroUtilities;

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
    public void setShiroUtilities(ShiroUtilities shiroUtilities) {
        this.shiroUtilities = shiroUtilities;
    }

    public void doConfirmPayment() throws PaymentException {
        SubscriptionPlan subscriptionPlan = paymentService.confirmPayment(paymentToken);
        if (subscriptionPlan != null && shiroUtilities.isUserAuthenticated()) {
            UserID userID = shiroUtilities.getCurrentUserID();
            Permission permission = Permission.valueOf(subscriptionPlan.name());
            switch (permission) {
                case ENTERPRISE:
                    userService.addUserPermission(userID, Permission.ENTERPRISE);
                case PRO:
                    userService.addUserPermission(userID, Permission.PRO);
                    break;
            }
            Collection<Realm> realms = ((DefaultWebSecurityManager) SecurityUtils.getSecurityManager()).getRealms();
            for (Realm realm : realms) {
                if (realm instanceof DatabaseUserRealm) {
                    ((DatabaseUserRealm) realm).clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
                }
            }
        }
    }

    public String getPaymentToken() {
        return paymentToken;
    }

    public void setPaymentToken(String paymentToken) {
        this.paymentToken = paymentToken;
    }
}
