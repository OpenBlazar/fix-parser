package com.blazarquant.bfp.services.payment;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.data.user.UserAddress;

/**
 * @author Wojciech Zankowski
 */
public interface PaymentService {

    String subscribe(SubscriptionPlan subscriptionPlan, UserAddress userAddress) throws PaymentException;

    SubscriptionPlan confirmPayment(String paymentToken) throws PaymentException;

}
