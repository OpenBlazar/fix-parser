package net.openblazar.bfp.core.parser;

import net.openblazar.bfp.core.parser.util.FixDelimiterResolver;
import net.openblazar.bfp.core.parser.util.FixMessageConverter;
import net.openblazar.bfp.data.fix.FixMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wojciech Zankowski
 */
public class FixParser {

    private static final Pattern fixPattern = Pattern.compile("[^0-9a-zA-Z:\\s]*8=FIX(.*?)[^0-9]10=\\d{3}.?");

    private final FixDelimiterResolver delimiterResolver =  new FixDelimiterResolver();
    private final FixMessageConverter messageConverter = new FixMessageConverter();

    public List<FixMessage> parseInput(String input) {
        List<String> textMessages = extractFixMessages(input);
        if(textMessages.isEmpty()) {
            return Collections.emptyList();
        }

        String delimiter = resolveMessageDelimiter(textMessages.get(0));
        return messageConverter.convertToFixMessages(textMessages, delimiter);
    }

    protected List<String> extractFixMessages(String input) {
        List<String> messages = new ArrayList<String>();
        Matcher matcher = fixPattern.matcher(input);
        while (matcher.find()) {
            messages.add(matcher.group());
        }
        return messages;
    }

    protected String resolveMessageDelimiter(String input) {
        int tag9Start = delimiterResolver.getTag9Index(input);
        int tag9Length = delimiterResolver.getTag9Length(input);
        int tag35Start = delimiterResolver.getTag35Index(input);
        return input.substring(tag9Start + tag9Length, tag35Start + 1);
    }

}
