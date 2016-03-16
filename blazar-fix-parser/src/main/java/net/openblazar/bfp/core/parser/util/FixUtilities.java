package net.openblazar.bfp.core.parser.util;

import net.openblazar.bfp.data.fix.FixField;
import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.data.fix.FixValue;

import java.util.Optional;

/**
 * @author Wojciech Zankowski
 */
public class FixUtilities {

    public static String getSender(FixMessage message) {
        Optional<FixValue> sender = message.getField(FixField.SenderCompID);
        return sender.isPresent() ? sender.get().getValue() : "Unknown";
    }

    public static String getReceiver(FixMessage message) {
        Optional<FixValue> receiver = message.getField(FixField.TargetCompID);
        return receiver.isPresent() ? receiver.get().getValue() : "Unknown";
    }

    public static String getSendingTime(FixMessage message) {
        Optional<FixValue> sendingTime = message.getField(FixField.SendingTime);
        return sendingTime.isPresent() ? sendingTime.get().getValue() : "Unknown";
    }

}
