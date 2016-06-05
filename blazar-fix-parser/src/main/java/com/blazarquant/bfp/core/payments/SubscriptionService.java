package com.blazarquant.bfp.core.payments;

import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.data.user.UserAddress;

/**
 * @author Wojciech Zankowski
 */
public interface SubscriptionService {

    String doSubscription(SubscriptionPlan subscriptionPlan, UserAddress userAddress) throws PaymentException;

    SubscriptionPlan confirmSubscription(String token) throws PaymentException;

}
