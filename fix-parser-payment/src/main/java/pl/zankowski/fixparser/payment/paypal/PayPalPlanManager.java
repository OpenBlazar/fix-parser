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

import pl.zankowski.bfp.core.payments.exception.PaymentException;
import pl.zankowski.fixparser.payment.api.exception.SubscriptionPlan;
import pl.zankowski.bfp.core.payments.paypal.util.PayPalPlanInitializer;
import com.paypal.api.payments.Plan;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wojciech Zankowski
 */
public class PayPalPlanManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPalPlanManager.class);

    private final Map<SubscriptionPlan, Plan> payPalPlanMap = new ConcurrentHashMap<>();
    private final PayPalPlanInitializer planInitializer;

    public PayPalPlanManager(String accessToken) throws IOException, PayPalRESTException {
        this.planInitializer = new PayPalPlanInitializer(accessToken);
        new Thread(() -> {
            try {
                initialize();
            } catch (IOException | PayPalRESTException e) {
                LOGGER.error("Failed to initialize plan. ", e);
            }
        }).start();
    }

    private void initialize() throws IOException, PayPalRESTException {
        payPalPlanMap.put(SubscriptionPlan.PRO, planInitializer.initializeProPlan());
        payPalPlanMap.put(SubscriptionPlan.ENTERPRISE, planInitializer.initializeEnterprisePlan());
    }

    protected Plan getPlan(SubscriptionPlan payPalPlan) throws PaymentException {
        Plan plan = payPalPlanMap.get(payPalPlan);
        if (plan == null) {
            throw new PaymentException("Failed to retrive " + payPalPlan.name() + " plan.");
        }
        return plan;
    }

}
