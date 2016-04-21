package com.blazarquant.bfp.core.mail.connection;

import javax.mail.MessagingException;

/**
 * @author Wojciech Zankowski
 */
public interface MailConnection {

    MailSession connect(String host, int port, String username, String password) throws MessagingException;

}
