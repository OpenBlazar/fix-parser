package pl.zankowski.fixparser.mail.spi;

public interface IMailService {

    void sendConfirmationLink(String confirmationKey, String userMail);

}
