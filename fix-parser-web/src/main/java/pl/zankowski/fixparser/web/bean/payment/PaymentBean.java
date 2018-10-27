package pl.zankowski.fixparser.web.bean.payment;

import pl.zankowski.bfp.core.payments.SubscriptionPlan;
import pl.zankowski.bfp.core.payments.exception.PaymentException;
import pl.zankowski.bfp.data.user.Permission;
import pl.zankowski.bfp.data.user.UserID;
import pl.zankowski.bfp.services.payment.PaymentService;
import pl.zankowski.bfp.services.user.UserService;
import pl.zankowski.bfp.web.bean.AbstractBean;
import pl.zankowski.bfp.web.util.ShiroUtils;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
