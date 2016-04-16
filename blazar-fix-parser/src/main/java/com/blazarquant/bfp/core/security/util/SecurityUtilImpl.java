package com.blazarquant.bfp.core.security.util;

import com.blazarquant.bfp.core.security.exception.DecodingException;
import org.apache.shiro.codec.Base64;
import org.mindrot.jbcrypt.BCrypt;

import java.util.UUID;

/**
 * @author Wojciech Zankowski
 */
public class SecurityUtilImpl implements SecurityUtil {

    public static final int GENSALT_ROUNDS = 12;
    public static final String KEY_DELIMITER = ";";

    @Override
    public String hashPassword(char[] password) {
        return BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt(GENSALT_ROUNDS));
    }

    @Override
    public boolean checkPassword(char[] password, String hashedPassword) {
        return BCrypt.checkpw(String.valueOf(password), hashedPassword);
    }

    @Override
    public String generateConfirmationKey(long id, String username, String email) {
        if (username.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Failed to generate confirmation key. Username or email is empty.");
        }
        return Base64.encodeToString(
                buildKeyBase(id, username, email).getBytes());
    }

    private String buildKeyBase(long id, String username, String email) {
        return String.valueOf(id) + KEY_DELIMITER + username + KEY_DELIMITER + email;
    }

    @Override
    public long decodeConfirmationKey(String confirmationKey) throws DecodingException {
        String[] elements = Base64.decodeToString(confirmationKey).split(KEY_DELIMITER);
        if (elements.length != 3) {
            throw new IllegalArgumentException("Illegal confirmation key. Please contact with admin.");
        }
        return Long.parseLong(elements[0]);
    }

    @Override
    public String generateShareKey() {
        return UUID.randomUUID().toString();
    }
}
