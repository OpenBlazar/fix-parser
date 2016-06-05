package com.blazarquant.bfp.core.payments.paypal.util;

import com.blazarquant.bfp.data.user.UserAddress;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Plan;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * @author Wojciech Zankowski
 */
public class PayPalAgreementFactory {

    public static final DateTimeFormatter START_DATE_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    public Agreement createAgreement(String planId, UserAddress userAddress) {
        Agreement agreement = new Agreement();
        agreement = agreement
                .setName("BlazarQuant Service Agreement")
                .setDescription("Agreement BlazarQuant service subscription")
                .setStartDate(START_DATE_FORMATTER.format(Instant.now().plusSeconds(120)))
                .setPlan(new Plan().setId(planId))
                .setPayer(new Payer().setPaymentMethod("paypal")).setShippingAddress((Address) new Address()
                        .setLine1(userAddress.getAddress())
                        .setCity(userAddress.getCity())
                        .setState(userAddress.getState())
                        .setPostalCode(userAddress.getPostalCode())
                        .setCountryCode(userAddress.getCountryCode()));
        return agreement;
    }

}
