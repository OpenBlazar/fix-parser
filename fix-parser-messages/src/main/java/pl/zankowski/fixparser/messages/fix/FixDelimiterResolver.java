package pl.zankowski.fixparser.messages.fix;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixDelimiterResolver {

    private static final Pattern TAG_9_PATTERN = Pattern.compile("[^0-9]9=[0-9]+");
    private static final Pattern TAG_35_PATTERN = Pattern.compile("[^0-9]35=[0-9A-Za-z]+");

    public int getTag9Index(final String input) {
        final Matcher matcher = TAG_9_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        }
        throw new IllegalArgumentException("Failed to find body length value.");
    }

    public int getTag9Length(final String input) {
        final Matcher matcher = TAG_9_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.group().length();
        }
        throw new IllegalArgumentException("Failed to find body length value.");
    }

    public int getTag35Index(final String input) {
        final Matcher matcher = TAG_35_PATTERN.matcher(input);
        if (matcher.find()) {
            return matcher.start();
        }
        throw new IllegalArgumentException("Failed to find message type value.");
    }

}
