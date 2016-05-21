package com.blazarquant.bfp.fix.parser.util;

import com.blazarquant.bfp.fix.data.*;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageConverter {

    public static final String FIELD_DELIMITER = "=";
    public static final int FIX_KEY = 0;
    public static final int FIX_VALUE = 1;
    public static final char ENTRY_DELIMITER = '\u0001';

    public List<FixMessage> convertToFixMessages(List<String> textMessages, String delimiter, FixDefinitionProvider definitionProvider) {
        List<FixMessage> messages = new ArrayList<>();
        long counter = 0;
        for (String textMessage : textMessages) {
            messages.add(convertToFixMessage(textMessage, delimiter, counter++, definitionProvider));
        }
        return messages;
    }

    public FixMessage convertToFixMessage(String textMessage, String delimiter, long counter, FixDefinitionProvider definitionProvider) {
        String[] fields = textMessage.split(delimiter);
        List<FixPair> messageFields = new ArrayList<>();

        for (String field : fields) {
            String[] values = field.split(FIELD_DELIMITER);
            if (values.length != 2) {
                continue;
            }
            int fixFieldTag = Integer.parseInt(values[FIX_KEY]);
            FixField fieldKey = definitionProvider.getFixField(fixFieldTag);
            FixValue fieldValue = definitionProvider.getFixValue(fixFieldTag, values[FIX_VALUE]);
            messageFields.add(new FixPair(fixFieldTag, fieldKey, fieldValue));
        }
        return toFixMessage(messageFields, counter);
    }

    protected FixMessage toFixMessage(List<FixPair> messageFields, long counter) {
        FixMessage.Builder messageBuilder = new FixMessage.Builder();
        FixPair version = getField(messageFields, 8);
        if (version != null) {
            messageBuilder.version(FixVersion.getFixVersionFromCode(version.getFixValue().getValue()));
        }
        FixPair messageType = getField(messageFields, 35);
        if (messageType != null) {
            messageBuilder.messageType(messageType);
        }
        messageBuilder.messageID(counter);
        messageBuilder.messageFields(messageFields);
        return messageBuilder.build();
    }

    private FixPair getField(List<FixPair> fixPairs, int tag) {
        List<FixPair> fields = fixPairs.stream()
                .filter(pair -> pair.getFixField().getTag() == tag)
                .collect(Collectors.toList());
        if (fields.isEmpty()) {
            return null;
        } else {
            return fields.get(0);
        }
    }

    public String convertToString(FixMessage fixMessage) {
        return convertToString(fixMessage, ENTRY_DELIMITER);
    }

    public String convertToString(FixMessage fixMessage, char entryDelimiter) {
        StringBuilder builder = new StringBuilder();
        fixMessage.getMessageFields().stream().forEach((pair) -> {
            builder.append(pair.getFixFieldTag());
            builder.append(FIELD_DELIMITER);
            builder.append(pair.getFixValue().getValue());
            builder.append(entryDelimiter);
        });
        return builder.toString();
    }

}
