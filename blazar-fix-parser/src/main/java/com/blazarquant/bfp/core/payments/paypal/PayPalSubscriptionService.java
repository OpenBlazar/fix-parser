package com.blazarquant.bfp.core.payments.paypal;

import com.blazarquant.bfp.core.payments.SubscriptionPlan;
import com.blazarquant.bfp.core.payments.SubscriptionService;
import com.blazarquant.bfp.core.payments.exception.PaymentException;
import com.blazarquant.bfp.core.payments.paypal.util.PayPalAgreementFactory;
import com.blazarquant.bfp.data.user.UserAddress;
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
    private final OAuthTokenCredential tokenCredential;

    private final Object accessTokenLock = new Object();

    private PayPalAccessTokenRefreshingThread payPalAccessTokenRefreshingThread;
    private String accessToken;

    public PayPalSubscriptionService(File payPalConfig) throws PayPalRESTException, IOException {
        this.tokenCredential = PayPalResource.initConfig(payPalConfig);
        this.payPalPlanManager = new PayPalPlanManager(tokenCredential.getAccessToken());
        this.agreementFactory = new PayPalAgreementFactory();
        this.payPalAgreementManager = new PayPalAgreementManager();
        this.accessToken = tokenCredential.getAccessToken();
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
                agreement = agreement.create(accessToken);
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
                    agreement = agreement.execute(accessToken);
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
        payPalAccessTokenRefreshingThread = new PayPalAccessTokenRefreshingThread(tokenCredential);
        payPalAccessTokenRefreshingThread.start();
    }

    private class PayPalAccessTokenRefreshingThread extends Thread {

        private static final String THREAD_NAME = "PayPal-AccessToken-Refreshing-Thread";
        private final long REFRESH_DELAY = TimeUnit.HOURS.toMillis(2);

        private AtomicBoolean stop = new AtomicBoolean(false);
        private OAuthTokenCredential tokenCredential;

        private PayPalAccessTokenRefreshingThread(OAuthTokenCredential tokenCredential) {
            super(THREAD_NAME);
            this.tokenCredential = tokenCredential;
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
                            accessToken = tokenCredential.getAccessToken();
                        } catch (PayPalRESTException e) {
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
