package com.blazarquant.bfp.core.payments.paypal;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.paypal.api.payments.Agreement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wojciech Zankowski
 */
public class PayPalAgreementManager {

    public static final String AGREEMENT_REL = "approval_url";

    private final Map<String, Agreement> agreementMap = new ConcurrentHashMap<>();
    private final Map<String, SubscriptionPlan> agreementTypeMap = new ConcurrentHashMap<>();

    public void saveAgreement(Agreement agreement, SubscriptionPlan subscriptionPlan) {
        agreementMap.put(agreement.getToken(), agreement);
        agreementTypeMap.put(agreement.getToken(), subscriptionPlan);
    }

    public SubscriptionPlan getSubscriptionTypeForToken(String token) {
        return agreementTypeMap.get(token);
    }

    public Agreement getAgreement(String token) {
        return agreementMap.get(token);
    }

    public void removeAgreement(Agreement agreement) {
        String token = agreement.getToken();
        if (token != null) {
            agreementMap.remove(token);
            agreementTypeMap.remove(token);
        }
    }

}
