package pl.zankowski.fixparser.payment;

import com.paypal.base.rest.PayPalRESTException;
import pl.zankowski.bfp.common.PathUtils;
import pl.zankowski.fixparser.payment.api.exception.SubscriptionPlan;
import pl.zankowski.bfp.core.payments.exception.PaymentException;
import pl.zankowski.bfp.core.payments.paypal.PayPalSubscriptionService;
import pl.zankowski.bfp.core.security.util.SettingsManager;
import pl.zankowski.bfp.data.user.UserAddress;
import pl.zankowski.fixparser.payment.spi.PaymentService;

import java.io.File;
import java.io.IOException;

public class PaymentServiceImpl implements PaymentService {
    
    private final PayPalSubscriptionService payPalSubscriptionService;

    public PaymentServiceImpl() throws PayPalRESTException, IOException {
        payPalSubscriptionService = new PayPalSubscriptionService(
                new File(
                        PathUtils.joinPath(SettingsManager.getInstance().getPathResolver().getAppDirectory(), "config", "paypal"),
                        "config.properties"
                ));
    }

    @Override
    public String subscribe(SubscriptionPlan subscriptionPlan, UserAddress userAddress) throws PaymentException {
        return payPalSubscriptionService.doSubscription(subscriptionPlan, userAddress);
    }

    @Override
    public SubscriptionPlan confirmPayment(String paymentToken) throws PaymentException {
        return payPalSubscriptionService.confirmSubscription(paymentToken);
    }
}
