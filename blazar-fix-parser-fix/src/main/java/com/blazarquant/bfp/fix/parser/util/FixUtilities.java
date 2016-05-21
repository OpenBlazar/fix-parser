package com.blazarquant.bfp.fix.parser.util;


import com.blazarquant.bfp.fix.data.FixEnum;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.data.FixValue;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class FixUtilities {

    private FixUtilities() {
        // Utilities class with static classes
    }

    public static String getSender(FixMessage message) {
        List<FixValue> sender = message.getField(FixEnum.SenderCompID.getTag());
        return sender.isEmpty() ? "Unknown" : sender.get(0).getValue();
    }

    public static String getReceiver(FixMessage message) {
        List<FixValue> receiver = message.getField(FixEnum.TargetCompID.getTag());
        return receiver.isEmpty() ? "Unknown" : receiver.get(0).getValue();
    }

    public static String getSendingTime(FixMessage message) {
        List<FixValue> sendingTime = message.getField(FixEnum.SendingTime.getTag());
        return sendingTime.isEmpty() ? "Unknown" : sendingTime.get(0).getValue();
    }

    public static String getOrdStatus(FixMessage message) {
        List<FixValue> ordStatus = message.getField(FixEnum.OrdStatus.getTag());
        return ordStatus.isEmpty() ? "" : ordStatus.get(0).getValue();
    }

    public static String getOrdStatusDescription(FixMessage message) {
        List<FixValue> ordStatus = message.getField(FixEnum.OrdStatus.getTag());
        return ordStatus.isEmpty() ? "" : ordStatus.get(0).getDescription();
    }

}
