package com.blazarquant.bfp.core.payments.paypal;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.paypal.api.payments.Agreement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Wojciech Zankowski
 */
public class PayPalAgreementManagerTest {

    private PayPalAgreementManager manager;

    @Before
    public void setUp() {
        manager = new PayPalAgreementManager();
    }

    @Test
    public void testOverallBehaviour() {
        final String token = "token";
        final Agreement agreement = new Agreement();
        agreement.setToken("token");
        final SubscriptionPlan plan = SubscriptionPlan.PRO;

        manager.saveAgreement(agreement, plan);
        assertEquals(plan, manager.getSubscriptionTypeForToken(token));
        assertEquals(agreement, manager.getAgreement(token));

        manager.removeAgreement(agreement);
        assertNull(manager.getSubscriptionTypeForToken(token));
        assertNull(manager.getAgreement(token));
    }

}
