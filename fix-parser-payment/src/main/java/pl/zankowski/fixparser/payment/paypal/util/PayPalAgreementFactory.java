package pl.zankowski.bfp.core.payments.paypal.util;

import pl.zankowski.bfp.data.user.UserAddress;
import com.paypal.api.payments.Address;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Plan;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class PayPalAgreementFactory {

    public static final String AGREEMENT_NAME = "BlazarQuant Service Agreement";
    public static final String AGREEMENT_DESCRIPTION = "Agreement BlazarQuant service subscription";
    public static final String PAYMENT_METHOD = "paypal";

    public static final DateTimeFormatter START_DATE_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    public Agreement createAgreement(String planId, UserAddress userAddress) {
        Agreement agreement = new Agreement();
        agreement = agreement
                .setName(AGREEMENT_NAME)
                .setDescription(AGREEMENT_DESCRIPTION)
                .setStartDate(START_DATE_FORMATTER.format(Instant.now().plusSeconds(120)))
                .setPlan(new Plan().setId(planId))
                .setPayer(new Payer().setPaymentMethod(PAYMENT_METHOD))
                .setShippingAddress((Address) new Address()
                        .setLine1(userAddress.getAddress())
                        .setCity(userAddress.getCity())
                        .setState(userAddress.getState())
                        .setPostalCode(userAddress.getPostalCode())
                        .setCountryCode(userAddress.getCountryCode()));
        return agreement;
    }

}
