package pl.zankowski.fixparser.mail.connection;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

public class MailSessionImpl implements MailSession {

    private final Session session;

    public MailSessionImpl(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public Transport getTransport(final String protocol) throws NoSuchProviderException {
        return session.getTransport(protocol);
    }

}
