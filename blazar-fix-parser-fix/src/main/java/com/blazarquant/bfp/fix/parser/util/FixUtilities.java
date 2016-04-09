package com.blazarquant.bfp.fix.parser.util;


import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.data.FixValue;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class FixUtilities {

    public static String getSender(FixMessage message) {
        List<FixValue> sender = message.getField(FixField.SenderCompID);
        return sender.isEmpty() ? "Unknown" : sender.get(0).getValue();
    }

    public static String getReceiver(FixMessage message) {
        List<FixValue> receiver = message.getField(FixField.TargetCompID);
        return receiver.isEmpty() ? "Unknown" : receiver.get(0).getValue();
    }

    public static String getSendingTime(FixMessage message) {
        List<FixValue> sendingTime = message.getField(FixField.SendingTime);
        return sendingTime.isEmpty() ? "Unknown" : sendingTime.get(0).getValue();
    }

    public static String getOrdStatus(FixMessage message) {
        List<FixValue> ordStatus = message.getField(FixField.OrdStatus);
        return ordStatus.isEmpty() ? "" : ordStatus.get(0).getValue();
    }

    public static String getOrdStatusDescription(FixMessage message) {
        List<FixValue> ordStatus = message.getField(FixField.OrdStatus);
        return ordStatus.isEmpty() ? "" : ordStatus.get(0).getDescription();
    }

}
