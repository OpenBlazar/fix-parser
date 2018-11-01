package pl.zankowski.fixparser.core.framework;

import java.util.Base64;

import static pl.zankowski.fixparser.core.framework.FixParserConstants.DEFAULT_CHARSET;

public class CodecUtil {

    public static String encodeBase64(final String data) {
        return Base64.getEncoder().encodeToString(data.getBytes(DEFAULT_CHARSET));
    }

    public static String decodeBase64(final String data) {
        return new String(Base64.getDecoder().decode(data), DEFAULT_CHARSET);
    }

}
