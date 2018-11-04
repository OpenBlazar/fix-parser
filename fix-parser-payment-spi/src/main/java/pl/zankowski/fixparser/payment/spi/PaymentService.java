package pl.zankowski.fixparser.payment.spi;

import pl.zankowski.fixparser.payment.api.ClientAddressTO;
import pl.zankowski.fixparser.payment.api.SubscriptionPlan;
import pl.zankowski.fixparser.payment.api.exception.PaymentException;
import pl.zankowski.fixparser.user.api.UserId;

public interface PaymentService {

    String subscribe(SubscriptionPlan subscriptionPlan, ClientAddressTO clientAddress) throws PaymentException;

    SubscriptionPlan confirmPayment(String paymentToken) throws PaymentException;

}
