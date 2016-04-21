package com.blazarquant.bfp.core.mail.data;

import com.blazarquant.bfp.core.mail.MailEngine;
import com.blazarquant.bfp.core.mail.connection.MailConnection;
import com.blazarquant.bfp.core.mail.connection.MailConnectionCredentials;
import com.blazarquant.bfp.core.mail.connection.MailSession;
import com.blazarquant.bfp.core.mail.connection.MailSessionImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class MailEngineTest {

    private static final String SENDER = "no-reply@blazarquant.com";

    private MailConnectionCredentials mailCredentials;
    private MailConnection mailConnection;
    private MailSession mailSession;
    private MailEngine mailEngine;

    private Session session;

    @Before
    public void setUp() throws MessagingException {
        mailCredentials = new MailConnectionCredentials("", 0, "", "");
        mailConnection = mock(MailConnection.class);
        mailSession = mock(MailSessionImpl.class);
        session = Session.getDefaultInstance(System.getProperties());
        when(mailSession.getSession()).thenReturn(session);
        when(mailConnection.connect(mailCredentials.getHost(),
                mailCredentials.getPort(),
                mailCredentials.getUsername(),
                mailCredentials.getPassword()))
                .thenReturn(mailSession);
        mailEngine = new MailEngine(mailConnection, mailCredentials);
    }

    @Test
    public void testMessageSending() throws MessagingException, IOException {
        Transport transport = mock(Transport.class);
        when(mailSession.getTransport("smtp")).thenReturn(transport);

        String RECIPIENT = "wojciech@zankowski.pl";
        String SUBJECT = "Test";
        String TEXT = "test";

        MimeMessage message = MailTestMessageFactory.createMessage(session, SENDER, RECIPIENT, SUBJECT, TEXT);

        mailEngine.start();
        mailEngine.sendMessage(TEXT, SUBJECT, RECIPIENT);

        final ArgumentCaptor<MimeMessage> captor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(transport).sendMessage(captor.capture(), eq(InternetAddress.parse(RECIPIENT)));
        final MimeMessage mailMessage = captor.getValue();

        assertEquals(message.getSubject(), mailMessage.getSubject());
        assertArrayEquals(message.getRecipients(Message.RecipientType.TO), mailMessage.getRecipients(Message.RecipientType.TO));
        assertEquals(message.getSender(), mailMessage.getSender());
    }

}
