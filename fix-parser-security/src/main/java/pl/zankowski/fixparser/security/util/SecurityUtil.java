package pl.zankowski.fixparser.security.util;

import pl.zankowski.bfp.core.security.exception.DecodingException;

public interface SecurityUtil {

    String hashPassword(char[] password);

    boolean checkPassword(char[] password, String hashedPassword);

    String generateConfirmationKey(long id, String username, String email);

    long decodeConfirmationKey(String confirmationKey) throws DecodingException;

    String generateShareKey();

    String encodeMessage(String message);

    String decodeMessage(String message);

}
