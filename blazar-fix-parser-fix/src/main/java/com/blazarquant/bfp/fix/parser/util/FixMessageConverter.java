package com.blazarquant.bfp.fix.parser.util;

import com.blazarquant.bfp.fix.data.*;
import com.blazarquant.bfp.fix.data.field.MsgType;

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

    public List<FixMessage> convertToFixMessages(List<String> textMessages, String delimiter) {
        List<FixMessage> messages = new ArrayList<>();
        long counter = 0;
        for (String textMessage : textMessages) {
            messages.add(convertToFixMessage(textMessage, delimiter, counter++));
        }
        return messages;
    }

    public FixMessage convertToFixMessage(String textMessage, String delimiter, long counter) {
        String[] fields = textMessage.split(delimiter);
        List<FixPair> messageFields = new ArrayList<>();

        for (String field : fields) {
            String[] values = field.split(FIELD_DELIMITER);
            if (values.length != 2) {
                continue;
            }
            FixField fieldKey = FixField.getFieldFromTag(Integer.parseInt(values[FIX_KEY]));
            FixValue fieldValue = toFixValue(values[FIX_VALUE], fieldKey);
            messageFields.add(new FixPair(fieldKey, fieldValue));
        }
        return toFixMessage(messageFields, counter);
    }

    protected FixValue toFixValue(String value, FixField field) {
        return new FixValue(value, field.getValueDescription(value));
    }

    protected FixMessage toFixMessage(List<FixPair> messageFields, long counter) {
        FixMessage.Builder messageBuilder = new FixMessage.Builder();
        FixValue version = getField(messageFields, FixField.BeginString);
        if (version != null) {
            messageBuilder.version(FixVersion.getFixVersionFromCode(version.getValue()));
        }
        FixValue messageType = getField(messageFields, FixField.MsgType);
        if (messageType != null) {
            messageBuilder.messageType(MsgType.getMsgTypeFromValue(messageType.getValue()));
        }
        messageBuilder.messageID(counter);
        messageBuilder.messageFields(messageFields);
        return messageBuilder.build();
    }

    private FixValue getField(List<FixPair> fixPairs, FixField fixField) {
        List<FixValue> fixValues = fixPairs.stream()
                .filter(pair -> pair.getFixField() == fixField)
                .map(pair -> pair.getFixValue())
                .collect(Collectors.toList());
        if (fixValues.isEmpty()) {
            return null;
        } else {
            return fixValues.get(0);
        }
    }

    public String convertToString(FixMessage fixMessage) {
        return convertToString(fixMessage, ENTRY_DELIMITER);
    }

    public String convertToString(FixMessage fixMessage, char entryDelimiter) {
        StringBuilder builder = new StringBuilder();
        fixMessage.getMessageFields().stream().forEach((pair) -> {
            builder.append(pair.getFixField().getTag());
            builder.append(FIELD_DELIMITER);
            builder.append(pair.getFixValue().getValue());
            builder.append(entryDelimiter);
        });
        return builder.toString();
    }

}
