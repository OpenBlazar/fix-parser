package com.blazarquant.bfp.core.mail.data;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class MailMessageTest {

    private static final String RECIPIENT = "Recipient";
    private static final String SUBJECT = "Subject";
    private static final String TEXT = "Text";

    @Test
    public void testMessageBuilder() {
        MailMessage emptyMessage = new MailMessage.Builder().build();

        assertEquals("", emptyMessage.getText());
        assertEquals("", emptyMessage.getSubject());
        assertEquals("", emptyMessage.getRecipient());

        MailMessage message = new MailMessage.Builder()
                .recipient(RECIPIENT)
                .subject(SUBJECT)
                .text(TEXT)
                .build();

        assertEquals(RECIPIENT, message.getRecipient());
        assertEquals(SUBJECT, message.getSubject());
        assertEquals(TEXT, message.getText());
    }

}
