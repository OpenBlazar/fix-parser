package pl.zankowski.fixparser;

import com.google.inject.AbstractModule;
import org.apache.shiro.guice.aop.ShiroAopModule;
import pl.zankowski.fixparser.mail.MailModule;
import pl.zankowski.fixparser.messages.MessageModule;
import pl.zankowski.fixparser.messages.share.ShareModule;
import pl.zankowski.fixparser.payment.PaymentModule;
import pl.zankowski.fixparser.tracker.TrackerModule;
import pl.zankowski.fixparser.user.UserModule;
import pl.zankowski.fixparser.web.WebModule;

public class FixParserModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new MailModule());
        install(new MessageModule());
        install(new ShareModule());
        install(new PaymentModule());
        install(new TrackerModule());
        install(new UserModule());
        install(new ShiroAopModule());
        install(new WebModule());
    }
    
}
