package pl.zankowski.fixparser.mail.connection;

import javax.mail.MessagingException;

public interface MailConnection {

    MailSession connect(String host, int port, String username, String password) throws MessagingException;

}
