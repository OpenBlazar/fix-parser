package pl.zankowski.fixparser.mail;

import com.google.inject.Inject;
import pl.zankowski.fixparser.mail.spi.MailService;
import pl.zankowski.fixparser.mail.util.MailMessageFactory;

import javax.mail.MessagingException;

public class MailServiceImpl implements MailService {

    private final MailEngine mailEngine;

    @Inject
    public MailServiceImpl(final MailEngine mailEngine) throws MessagingException {
        this.mailEngine = mailEngine;
        this.mailEngine.start();
    }

    @Override
    public void sendConfirmationLink(String confirmationKey, String userMail) {
        sendMessage(MailMessageFactory.confirmationMessage(confirmationKey),
                MailMessageFactory.CONFIRMATION_MESSAGE_SUBJECT, userMail);
    }

    private void sendMessage(String message, String subject, String userMail) {
        mailEngine.sendMessage(message, subject, userMail);
    }

}
