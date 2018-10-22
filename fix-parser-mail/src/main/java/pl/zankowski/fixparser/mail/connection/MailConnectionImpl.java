package pl.zankowski.fixparser.mail.connection;

import javax.mail.MessagingException;
import javax.mail.Session;

public class MailConnectionImpl implements MailConnection {

    public MailSession connect(String host, int port, String username, String password) throws MessagingException {
        Session session = Session.getDefaultInstance(System.getProperties(), null);
        session.getTransport().connect(host, port, username, password);
        return new MailSessionImpl(session);
    }

}
