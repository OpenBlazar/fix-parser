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
package pl.zankowski.bfp.core.payments.paypal.util;

import pl.zankowski.bfp.data.user.UserAddress;
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
