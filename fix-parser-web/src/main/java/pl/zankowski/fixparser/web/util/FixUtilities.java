package pl.zankowski.fixparser.web.util;

import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.web.model.FixEnum;

import java.util.List;

public class FixUtilities {

    private static final String UNKNOWN = "Unknown";
    private static final String EMPTY = "";

    private FixUtilities() {
        // Utilities class with static classes
    }

    public static String getSender(FixMessageTO message) {
        final List<FixValueTO> sender = message.getField(FixEnum.SenderCompID.getTag());
        return sender.isEmpty() ? UNKNOWN : sender.get(0).getValue();
    }

    public static String getReceiver(FixMessageTO message) {
        final List<FixValueTO> receiver = message.getField(FixEnum.TargetCompID.getTag());
        return receiver.isEmpty() ? UNKNOWN : receiver.get(0).getValue();
    }

    public static String getSendingTime(FixMessageTO message) {
        final List<FixValueTO> sendingTime = message.getField(FixEnum.SendingTime.getTag());
        return sendingTime.isEmpty() ? UNKNOWN : sendingTime.get(0).getValue();
    }

    public static String getOrdStatus(FixMessageTO message) {
        final List<FixValueTO> ordStatus = message.getField(FixEnum.OrdStatus.getTag());
        return ordStatus.isEmpty() ? EMPTY : ordStatus.get(0).getValue();
    }

    public static String getOrdStatusDescription(FixMessageTO message) {
        final List<FixValueTO> ordStatus = message.getField(FixEnum.OrdStatus.getTag());
        return ordStatus.isEmpty() ? EMPTY : ordStatus.get(0).getDescription();
    }

}
