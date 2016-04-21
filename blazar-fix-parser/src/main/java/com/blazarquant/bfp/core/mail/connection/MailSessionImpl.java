package com.blazarquant.bfp.core.mail.connection;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author Wojciech Zankowski
 */
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
    public Transport getTransport(String protocol) throws NoSuchProviderException {
        return session.getTransport(protocol);
    }

}
