package pl.zankowski.fixparser.payment;

import pl.zankowski.fixparser.payment.api.ClientAddressTO;
import pl.zankowski.fixparser.payment.api.SubscriptionPlan;
import pl.zankowski.fixparser.payment.api.exception.PaymentException;

public interface SubscriptionService {

    String doSubscription(SubscriptionPlan subscriptionPlan, ClientAddressTO userAddress) throws PaymentException;

    SubscriptionPlan confirmSubscription(String token) throws PaymentException;

}
