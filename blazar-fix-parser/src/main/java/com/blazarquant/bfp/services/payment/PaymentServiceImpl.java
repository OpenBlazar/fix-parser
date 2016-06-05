package com.blazarquant.bfp.services.payment;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.core.payments.paypal.PayPalSubscriptionService;
import com.blazarquant.bfp.data.user.UserAddress;
import com.paypal.base.rest.PayPalRESTException;

import java.io.File;
import java.io.IOException;

/**
 * @author Wojciech Zankowski
 */
public class PaymentServiceImpl implements PaymentService {

    private static final String CONFIG_PATH = System.getProperty("jboss.server.base.dir") + "/config/paypal";
    private final PayPalSubscriptionService payPalSubscriptionService;

    public PaymentServiceImpl() throws PayPalRESTException, IOException {
        payPalSubscriptionService = new PayPalSubscriptionService(
                new File(CONFIG_PATH, "config.properties"));
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
