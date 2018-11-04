package pl.zankowski.fixparser.payment;

import com.google.inject.AbstractModule;
import pl.zankowski.fixparser.payment.paypal.PayPalSubscriptionService;
import pl.zankowski.fixparser.payment.spi.PaymentService;

public class PaymentModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PaymentService.class).to(DefaultPaymentService.class).asEagerSingleton();
        bind(SubscriptionService.class).to(PayPalSubscriptionService.class).asEagerSingleton();
    }
}
