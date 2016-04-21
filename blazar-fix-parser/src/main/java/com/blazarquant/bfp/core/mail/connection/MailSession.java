package com.blazarquant.bfp.core.mail.connection;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

/**
 * @author Wojciech Zankowski
 */
public interface MailSession {

    Transport getTransport(String protocol) throws NoSuchProviderException;

    Session getSession();

}
