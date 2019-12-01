package pl.zankowski.fixparser.web.util;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.web.model.FixEnum;

import java.util.List;
import java.util.Optional;

public class FixUtilities {

    private static final String UNKNOWN = "Unknown";
    private static final String EMPTY = "";

    private FixUtilities() {
        // Utilities class with static classes
    }

    public static Optional<FixPairTO> getField(FixMessageTO message, int tag) {
        final List<FixPairTO> messageFields = message.getMessageFields();
        return messageFields.stream()
                .filter(pair -> pair.getFixField().getTag() == tag)
                .findFirst();
    }

    public static String getFieldValue(FixMessageTO message, int tag) {
        return getField(message, tag)
                .map(FixPairTO::getFixValue)
                .map(FixValueTO::getValue)
                .orElse(UNKNOWN);
    }

    public static String getSender(FixMessageTO message) {
        return getFieldValue(message, FixEnum.SenderCompID.getTag());
    }

    public static String getReceiver(FixMessageTO message) {
        return getFieldValue(message, FixEnum.TargetCompID.getTag());
    }

    public static String getSendingTime(FixMessageTO message) {
        return getFieldValue(message, FixEnum.SendingTime.getTag());
    }

    public static String getOrdStatus(FixMessageTO message) {
        return getFieldValue(message, FixEnum.OrdStatus.getTag());
    }

    public static String getOrdStatusDescription(FixMessageTO message) {
        return getField(message, FixEnum.OrdStatus.getTag())
                .map(FixPairTO::getFixValue)
                .map(FixValueTO::getDescription)
                .orElse(UNKNOWN);
    }

}
