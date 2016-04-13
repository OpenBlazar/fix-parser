package com.blazarquant.bfp.services.mail;

/**
 * @author Wojciech Zankowski
 */
public interface MailService {

    void sendConfirmationLink(String confirmationKey, String userMail);

    void sendMessage(String message, String subject, String userMail);

}
