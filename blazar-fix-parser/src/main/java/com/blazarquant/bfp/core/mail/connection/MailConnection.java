package com.blazarquant.bfp.core.mail.connection;

import javax.mail.MessagingException;
import javax.mail.Session;

/**
 * @author Wojciech Zankowski
 */
public interface MailConnection {

    Session connect(String host, int port, String username, String password) throws MessagingException;

}
