package com.blazarquant.bfp.fix.parser.util;


import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.data.FixValue;

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

    public static String getOrdStatus(FixMessage message) {
        Optional<FixValue> ordStatus = message.getField(FixField.OrdStatus);
        return ordStatus.isPresent() ? ordStatus.get().getValue() : "";
    }

    public static String getOrdStatusDescription(FixMessage message) {
        Optional<FixValue> ordStatus = message.getField(FixField.OrdStatus);
        return ordStatus.isPresent() ? ordStatus.get().getDescription() : "";
    }

}
