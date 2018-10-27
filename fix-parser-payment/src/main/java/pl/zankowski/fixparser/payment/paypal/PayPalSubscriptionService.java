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
import pl.zankowski.bfp.core.payments.SubscriptionService;
import pl.zankowski.bfp.core.payments.exception.PaymentException;
import pl.zankowski.bfp.core.payments.paypal.util.PayPalAgreementFactory;
import pl.zankowski.bfp.data.user.UserAddress;
import com.paypal.api.payments.Agreement;
import com.paypal.api.payments.Links;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Wojciech Zankowski
 */
public class PayPalSubscriptionService implements SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PayPalSubscriptionService.class);

    private final PayPalPlanManager payPalPlanManager;
    private final PayPalAgreementManager payPalAgreementManager;
    private final PayPalAgreementFactory agreementFactory;
    private OAuthTokenCredential tokenCredential;

    private final Object accessTokenLock = new Object();

    private PayPalAccessTokenRefreshingThread payPalAccessTokenRefreshingThread;

    public PayPalSubscriptionService(File payPalConfig) throws PayPalRESTException, IOException {
        this.tokenCredential = PayPalResource.initConfig(payPalConfig);
        this.payPalPlanManager = new PayPalPlanManager(tokenCredential.getAccessToken());
        this.agreementFactory = new PayPalAgreementFactory();
        this.payPalAgreementManager = new PayPalAgreementManager();
        startPayPalAccessTokenRefreshingThread();
    }

    @Override
    public String doSubscription(SubscriptionPlan subscriptionPlan, UserAddress userAddress) throws PaymentException {
        try {
            Agreement agreement = agreementFactory.createAgreement(
                    payPalPlanManager.getPlan(subscriptionPlan).getId(),
                    userAddress
            );
            synchronized (accessTokenLock) {
                agreement = agreement.create(tokenCredential.getAccessToken());
            }

            payPalAgreementManager.saveAgreement(agreement, subscriptionPlan);

            List<Links> agreementLinks = agreement.getLinks();
            for (Links link : agreementLinks) {
                if (PayPalAgreementManager.AGREEMENT_REL.equals(link.getRel())) {
                    return link.getHref();
                }
            }
            throw new PaymentException("Failed to realize subscription. No approval url.");
        } catch (Exception e) {
            LOGGER.error("Failed to realize subscription.", e);
            throw new PaymentException("Failed to realize subscription. " + e.getMessage());
        }
    }

    @Override
    public SubscriptionPlan confirmSubscription(String token) throws PaymentException {
        try {
            Agreement agreement = payPalAgreementManager.getAgreement(token);
            if (agreement != null) {
                synchronized (accessTokenLock) {
                    agreement = agreement.execute(tokenCredential.getAccessToken());
                }
                final SubscriptionPlan subscriptionPlan = payPalAgreementManager.getSubscriptionTypeForToken(token);

                payPalAgreementManager.removeAgreement(agreement);

                return subscriptionPlan;
            }
            throw new PaymentException("Failed to resolve subscription type.");
        } catch (Exception e) {
            LOGGER.error("Failed to confirm subscription.", e);
            throw new PaymentException("Failed to confirm subscription. " + e.getMessage());
        }
    }

    private void startPayPalAccessTokenRefreshingThread() {
        payPalAccessTokenRefreshingThread = new PayPalAccessTokenRefreshingThread();
        payPalAccessTokenRefreshingThread.start();
    }

    private class PayPalAccessTokenRefreshingThread extends Thread {

        private static final String THREAD_NAME = "PayPal-AccessToken-Refreshing-Thread";
        private final long REFRESH_DELAY = TimeUnit.MINUTES.toMillis(2);
        private final long EXPIRE_TIME = 10000;

        private AtomicBoolean stop = new AtomicBoolean(false);

        private PayPalAccessTokenRefreshingThread() {
            super(THREAD_NAME);
        }

        public void sendStop() {
            stop.set(true);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(REFRESH_DELAY);
                    if (stop.get()) {
                        break;
                    }
                    synchronized (accessTokenLock) {
                        try {
                            if (tokenCredential.expiresIn() <= EXPIRE_TIME) {
                                tokenCredential = PayPalResource.getOAuthTokenCredential();
                            }
                        } catch (Exception e) {
                            LOGGER.error("Failed to generate new access token.", e);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
