package com.blazarquant.bfp.services.mail;

import com.blazarquant.bfp.core.mail.MailEngine;
import com.blazarquant.bfp.core.mail.util.MailMessageFactory;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;

/**
 * @author Wojciech Zankowski
 */
public class MailServiceImpl implements MailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    private final MailEngine mailEngine;

    @Inject
    public MailServiceImpl(MailEngine mailEngine) throws MessagingException {
        this.mailEngine = mailEngine;
        this.mailEngine.start();
    }

    @Override
    public void sendConfirmationLink(String confirmationKey, String userMail) {
        sendMessage(MailMessageFactory.confirmationMessage(confirmationKey), "BlazarQuant - Register confirmation!", userMail);
    }

    @Override
    public void sendMessage(String message, String subject, String userMail) {
        mailEngine.sendMessage(message, subject, userMail);
    }

}
