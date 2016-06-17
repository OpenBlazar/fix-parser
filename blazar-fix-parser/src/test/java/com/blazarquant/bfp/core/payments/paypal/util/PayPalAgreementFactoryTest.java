package com.blazarquant.bfp.core.payments.paypal.util;

import com.blazarquant.bfp.core.payments.paypal.util.PayPalAgreementFactory;
import com.blazarquant.bfp.data.payments.paypal.PayPalCountryCodes;
import com.blazarquant.bfp.data.user.UserAddress;
import com.paypal.api.payments.Agreement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class PayPalAgreementFactoryTest {

    private PayPalAgreementFactory factory;

    @Before
    public void setUp() {
        factory = new PayPalAgreementFactory();
    }

    @Test
    public void testValidAgreement() {
        final UserAddress userAddress = new UserAddress.Builder()
                .address("ADR")
                .city("CITY")
                .postalCode("PC")
                .state("ST")
                .countrycode(PayPalCountryCodes.POLAND.getCountryCode())
                .build();
        final String planId = "9";

        Agreement agreement = factory.createAgreement(planId, userAddress);
        assertEquals(PayPalAgreementFactory.AGREEMENT_NAME, agreement.getName());
        assertEquals(PayPalAgreementFactory.AGREEMENT_DESCRIPTION, agreement.getDescription());
        assertEquals(planId, agreement.getPlan().getId());
        assertEquals(userAddress.getAddress(), agreement.getShippingAddress().getLine1());
        assertEquals(userAddress.getCity(), agreement.getShippingAddress().getCity());
        assertEquals(userAddress.getState(), agreement.getShippingAddress().getState());
        assertEquals(userAddress.getPostalCode(), agreement.getShippingAddress().getPostalCode());
        assertEquals(userAddress.getCountryCode(), agreement.getShippingAddress().getCountryCode());
    }

}
