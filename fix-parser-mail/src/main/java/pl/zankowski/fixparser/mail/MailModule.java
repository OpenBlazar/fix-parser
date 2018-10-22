package pl.zankowski.fixparser.mail;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import pl.zankowski.fixparser.mail.connection.MailConnection;
import pl.zankowski.fixparser.mail.connection.MailConnectionImpl;

public class MailModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MailConnection.class).to(MailConnectionImpl.class);
        bind(MailEngine.class);
        bind(MailService.class).to(MailServiceImpl.class).in(Singleton.class);
    }

}
