package pl.zankowski.fixparser;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.shiro.guice.aop.ShiroAopModule;
import pl.zankowski.fixparser.mail.MailModule;
import pl.zankowski.fixparser.messages.MessageModule;
import pl.zankowski.fixparser.messages.share.ShareModule;
import pl.zankowski.fixparser.payment.PaymentModule;
import pl.zankowski.fixparser.tracker.TrackerModule;
import pl.zankowski.fixparser.user.UserModule;
import pl.zankowski.fixparser.web.WebModule;
import pl.zankowski.fixparser.web.security.SecurityModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class FixParser extends GuiceServletContextListener {

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new MailModule(),
                new MessageModule(),
                new ShareModule(),
                new PaymentModule(),
                new TrackerModule(),
                new UserModule(),
                new SecurityModule(servletContext),
                new ShiroAopModule(),
                new WebModule());
    }

}
