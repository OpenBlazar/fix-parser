package pl.zankowski.fixparser.mail.connection;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

public interface MailSession {

    Transport getTransport(String protocol) throws NoSuchProviderException;

    Session getSession();

}
