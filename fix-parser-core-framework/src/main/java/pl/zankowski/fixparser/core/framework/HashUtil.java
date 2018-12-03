package pl.zankowski.fixparser.core.framework;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {

    private static final int GENSALT_ROUNDS = 12;

    public static String hash(char[] value) {
        return BCrypt.hashpw(String.valueOf(value), BCrypt.gensalt(GENSALT_ROUNDS));
    }

    public static boolean checkHash(char[] value, String hashedValue) {
        return BCrypt.checkpw(String.valueOf(value), hashedValue);
    }

}
