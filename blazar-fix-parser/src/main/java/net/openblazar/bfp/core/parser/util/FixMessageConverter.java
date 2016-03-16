package net.openblazar.bfp.core.parser.util;

import net.openblazar.bfp.data.fix.*;
import net.openblazar.bfp.data.fix.field.MsgType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
        Map<FixField, FixValue> messageFields = new LinkedHashMap<>();

        for (String field : fields) {
            String[] values = field.split(FIELD_DELIMITER);
            if (values.length != 2) {
                continue;
            }
            FixField fieldKey = FixField.getFieldFromTag(Integer.parseInt(values[FIX_KEY]));
            FixValue fieldValue = toFixValue(values[FIX_VALUE], fieldKey);
            messageFields.put(fieldKey, fieldValue);
        }
        return toFixMessage(messageFields, counter);
    }

    protected FixValue toFixValue(String value, FixField field) {
        return new FixValue(value, field.getValueDescription(value));
    }

    protected FixMessage toFixMessage(Map<FixField, FixValue> messageFields, long counter) {
        FixMessage.Builder messageBuilder = new FixMessage.Builder();
        FixValue version = messageFields.get(FixField.BeginString);
        if (version != null) {
            messageBuilder.version(FixVersion.getFixVersionFromCode(version.getValue()));
        }
        FixValue messageType = messageFields.get(FixField.MsgType);
        if (messageType != null) {
            messageBuilder.messageType(MsgType.getMsgTypeFromValue(messageType.getValue()));
        }
        messageBuilder.messageID(counter);
        messageBuilder.messageFields(messageFields);
        return messageBuilder.build();
    }

    public String convertToString(FixMessage fixMessage) {
        return convertToString(fixMessage, ENTRY_DELIMITER);
    }

    public String convertToString(FixMessage fixMessage, char entryDelimiter) {
        StringBuilder builder = new StringBuilder();
        fixMessage.getMessageFields().entrySet().stream().forEach((entry) -> {
            builder.append(entry.getKey().getTag());
            builder.append(FIELD_DELIMITER);
            builder.append(entry.getValue().getValue());
            builder.append(entryDelimiter);
        });
        return builder.toString();
    }

}
