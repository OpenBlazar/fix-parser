package com.blazarquant.bfp.services.mail;

import com.blazarquant.bfp.core.mail.MailEngine;
import com.blazarquant.bfp.core.mail.util.MailMessageFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.mail.MessagingException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class MailServiceImplTest {

    private MailEngine mailEngine;
    private MailService mailService;

    @Before
    public void setUp() throws MessagingException {
        mailEngine = mock(MailEngine.class);
        mailService = new MailServiceImpl(mailEngine);
    }

    @Test
    public void testSendConfirmationLink() {
        final String confirmationKey = "key";
        final String userMail = "test@test.com";

        mailService.sendConfirmationLink(confirmationKey, userMail);

        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userMailCaptor = ArgumentCaptor.forClass(String.class);
        verify(mailEngine, times(1)).sendMessage(messageCaptor.capture(), subjectCaptor.capture(), userMailCaptor.capture());
        assertEquals(MailMessageFactory.confirmationMessage(confirmationKey), messageCaptor.getValue());
        assertEquals(MailMessageFactory.CONFIRMATION_MESSAGE_SUBJECT, subjectCaptor.getValue());
        assertEquals(userMail, userMailCaptor.getValue());
    }

    @Test
    public void testSendMessage() {
        final String message = "message";
        final String subject = "subject";
        final String userMail = "mail@mail.com";

        mailService.sendMessage(message, subject, userMail);
        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userMailCaptor = ArgumentCaptor.forClass(String.class);
        verify(mailEngine, times(1)).sendMessage(messageCaptor.capture(), subjectCaptor.capture(), userMailCaptor.capture());
        assertEquals(message, messageCaptor.getValue());
        assertEquals(subject, subjectCaptor.getValue());
        assertEquals(userMail, userMailCaptor.getValue());
    }

}
