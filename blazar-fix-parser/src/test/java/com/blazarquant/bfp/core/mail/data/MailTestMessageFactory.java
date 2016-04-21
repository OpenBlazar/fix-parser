package com.blazarquant.bfp.core.mail.data;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;

/**
 * @author Wojciech Zankowski
 */
public class MailTestMessageFactory {

    public static MimeMessage createMessage(Session session, String sender, String recipient, String subject, String message)
            throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(sender));
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        mimeMessage.setSubject(subject);

        MimeBodyPart bodyPart = new MimeBodyPart();
        bodyPart.setContent(message, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(bodyPart);

        mimeMessage.setContent(multipart);
        mimeMessage.saveChanges();

        return mimeMessage;
    }

}
