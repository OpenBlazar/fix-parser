package net.openblazar.bfp.core.parser.util;

import net.openblazar.bfp.data.fix.FixField;
import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.data.fix.FixMessageType;
import net.openblazar.bfp.data.fix.FixVersion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageConverter {

    public static final String FIELD_DELIMITER = "=";
    public static final int FIX_KEY = 0;
    public static final int FIX_VALUE = 1;

    public List<FixMessage> convertToFixMessages(List<String> textMessages, String delimiter) {
        List<FixMessage> messages = new ArrayList<>();
        for (String textMessage : textMessages) {
            messages.add(convertToFixMessage(textMessage, delimiter));
        }
        return messages;
    }

    public FixMessage convertToFixMessage(String textMessage, String delimiter) {
        String[] fields = textMessage.split(delimiter);
        Map<FixField, String> messageFields = new HashMap<>();

        for (String field : fields) {
            String[] values = field.split(FIELD_DELIMITER);
            if (values.length != 2) {
                continue;
            }
            FixField fieldKey = FixField.getFieldFromCode(Integer.parseInt(values[FIX_KEY]));
            messageFields.put(fieldKey, values[FIX_VALUE]);
        }
        return toFixMessage(messageFields);
    }

    protected FixMessage toFixMessage(Map<FixField, String> messageFields) {
        FixMessage.Builder messageBuilder = new FixMessage.Builder();
        String textVersion = messageFields.get(FixField.BeginString);
        if (textVersion != null) {
            messageBuilder.version(FixVersion.getFixVersionFromCode(textVersion));
        }
        String textMessageType = messageFields.get(FixField.MsgType);
        if (textMessageType != null) {
            messageBuilder.messageType(FixMessageType.getMessageTypeFromCode(Integer.parseInt(textMessageType)));
        }
        messageBuilder.messageFields(messageFields);
        return messageBuilder.build();
    }

}
