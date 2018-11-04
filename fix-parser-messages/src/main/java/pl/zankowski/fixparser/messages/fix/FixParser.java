package pl.zankowski.fixparser.messages.fix;

import com.google.common.collect.Lists;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixParser {

    private static final Pattern FIX_PATTERN = Pattern.compile("[^0-9a-zA-Z!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?\\s]*8=FIX(.*?)[^0-9]10=\\d{3}.?");

    private final FixDelimiterResolver delimiterResolver = new FixDelimiterResolver();
    private final FixMessageConverter messageConverter = new FixMessageConverter();

    public List<FixMessageTO> parseInput(final String input, final FixDefinitionProvider definitionProvider) {
        final List<String> textMessages = extractFixMessages(input);
        if (textMessages.isEmpty()) {
            return Collections.emptyList();
        }

        final String delimiter = resolveMessageDelimiter(textMessages.get(0));
        return messageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
    }

    protected List<String> extractFixMessages(final String input) {
        final List<String> messages = Lists.newArrayList();
        final Matcher matcher = FIX_PATTERN.matcher(input);

        while (matcher.find()) {
            messages.add(matcher.group());
        }

        return messages;
    }

    protected String resolveMessageDelimiter(final String input) {
        final int tag9Start = delimiterResolver.getTag9Index(input);
        final int tag9Length = delimiterResolver.getTag9Length(input);
        final int tag35Start = delimiterResolver.getTag35Index(input);

        return input.substring(tag9Start + tag9Length, tag35Start + 1);
    }

}
