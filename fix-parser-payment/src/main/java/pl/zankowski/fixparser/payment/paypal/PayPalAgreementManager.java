/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.zankowski.bfp.core.payments.paypal;

import pl.zankowski.fixparser.payment.api.exception.SubscriptionPlan;
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
