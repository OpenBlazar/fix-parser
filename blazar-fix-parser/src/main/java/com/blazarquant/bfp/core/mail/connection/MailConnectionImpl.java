package com.blazarquant.bfp.core.mail.connection;

import javax.mail.*;

/**
 * @author Wojciech Zankowski
 */
public class MailConnectionImpl implements MailConnection {

    public MailSession connect(String host, int port, String username, String password) throws MessagingException {
        Session session = Session.getDefaultInstance(System.getProperties(), null);
        session.getTransport().connect(host, port, username, password);
        return new MailSessionImpl(session);
    }

}
