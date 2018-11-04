package pl.zankowski.fixparser.payment;

import com.paypal.base.rest.PayPalRESTException;
import pl.zankowski.fixparser.common.PathUtils;
import pl.zankowski.fixparser.payment.api.ClientAddressTO;
import pl.zankowski.fixparser.payment.api.SubscriptionPlan;
import pl.zankowski.fixparser.payment.api.exception.PaymentException;
import pl.zankowski.fixparser.payment.paypal.PayPalSubscriptionService;
import pl.zankowski.fixparser.payment.spi.PaymentService;

import java.io.File;
import java.io.IOException;

public class DefaultPaymentService implements PaymentService {
    
    private final PayPalSubscriptionService payPalSubscriptionService;

    public DefaultPaymentService() throws PayPalRESTException, IOException {
        payPalSubscriptionService = new PayPalSubscriptionService(
                new File(
                        PathUtils.joinPath(".", "config", "paypal"),
                        "config.properties"
                ));
    }

    @Override
    public String subscribe(SubscriptionPlan subscriptionPlan, ClientAddressTO userAddress) throws PaymentException {
        return payPalSubscriptionService.doSubscription(subscriptionPlan, userAddress);
    }

    @Override
    public SubscriptionPlan confirmPayment(String paymentToken) throws PaymentException {
        return payPalSubscriptionService.confirmSubscription(paymentToken);
    }
}
