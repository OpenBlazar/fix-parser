package pl.zankowski.fixparser.web.bean.payment;

import com.google.inject.Inject;
import pl.zankowski.fixparser.payment.api.SubscriptionPlan;
import pl.zankowski.fixparser.payment.api.exception.PaymentException;
import pl.zankowski.fixparser.payment.spi.PaymentService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.spi.UserService;
import pl.zankowski.fixparser.web.bean.AbstractBean;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named("paymentBean")
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
            UserId userID = shiroUtils.getCurrentUserID();
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
