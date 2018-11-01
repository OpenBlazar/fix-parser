package pl.zankowski.fixparser.core.framework;

import java.util.UUID;

public class RandomUtil {

    public static String generateRandomKey() {
        return UUID.randomUUID().toString();
    }

}
