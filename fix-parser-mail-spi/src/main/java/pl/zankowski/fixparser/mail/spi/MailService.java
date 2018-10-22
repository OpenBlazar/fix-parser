package pl.zankowski.fixparser.mail.spi;

public interface MailService {

    void sendConfirmationLink(String confirmationKey, String userMail);

}
