package com.blazarquant.bfp.fix.parser.util;

import com.blazarquant.bfp.fix.data.*;
import com.blazarquant.bfp.fix.data.field.MsgType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageFactory {

    public List<FixMessage> prepareFixMessagesForLegalFixLong() {
        List<FixMessage> messages = new ArrayList<>();
        messages.add(legalFixMessageLong1());
        messages.add(legalFixMessageLong2());
        messages.add(legalFixMessageLong3());
        return messages;
    }

    public FixMessage legalFixMessageLong1() {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(8, FixField.BeginString, new FixValue("FIX.4.2")));
        message.add(new FixPair(9, FixField.BodyLength, new FixValue("198")));
        message.add(new FixPair(35, FixField.MsgType, new FixValue("8", "ExecutionReport")));
        message.add(new FixPair(6, FixField.AvgPx, new FixValue("41.21")));
        message.add(new FixPair(10, FixField.CheckSum, new FixValue("024")));
        return new FixMessage(0L, FixVersion.FIX_42, MsgType.ExecutionReport, message);
    }

    public FixMessage legalFixMessageLong2() {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(8, FixField.BeginString, new FixValue("FIX.4.2")));
        message.add(new FixPair(9, FixField.BodyLength, new FixValue("204")));
        message.add(new FixPair(35, FixField.MsgType, new FixValue("8", "ExecutionReport")));
        message.add(new FixPair(6, FixField.AvgPx, new FixValue("102.75")));
        message.add(new FixPair(11, FixField.ClOrdID, new FixValue("38400195")));
        message.add(new FixPair(10, FixField.CheckSum, new FixValue("152")));
        return new FixMessage(1L, FixVersion.FIX_42, MsgType.ExecutionReport, message);
    }

    public FixMessage legalFixMessageLong3() {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(8, FixField.BeginString, new FixValue("FIX.4.2")));
        message.add(new FixPair(9, FixField.BodyLength, new FixValue("198")));
        message.add(new FixPair(35, FixField.MsgType, new FixValue("8", "ExecutionReport")));
        message.add(new FixPair(6, FixField.AvgPx, new FixValue("41.21")));
        message.add(new FixPair(11, FixField.ClOrdID, new FixValue("3840019")));
        message.add(new FixPair(14, FixField.CumQty, new FixValue("102")));
        message.add(new FixPair(10, FixField.CheckSum, new FixValue("024")));
        return new FixMessage(2L, FixVersion.FIX_42, MsgType.ExecutionReport, message);
    }

    public FixMessage legalFixMessage4() {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(8, FixField.BeginString, new FixValue("FIX.4.2")));
        message.add(new FixPair(9, FixField.BodyLength, new FixValue("197")));
        message.add(new FixPair(35, FixField.MsgType, new FixValue("8", "ExecutionReport")));
        message.add(new FixPair(6, FixField.AvgPx, new FixValue("0.0")));
        message.add(new FixPair(11, FixField.ClOrdID, new FixValue("373009")));
        message.add(new FixPair(14, FixField.CumQty, new FixValue("0")));
        message.add(new FixPair(17, FixField.ExecID, new FixValue("373009")));
        return new FixMessage(0L, FixVersion.FIX_42, MsgType.ExecutionReport, message);
    }

    public FixMessage legalFixMessage6() {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(8, FixField.BeginString, new FixValue("FIX.4.2")));
        message.add(new FixPair(9, FixField.BodyLength, new FixValue("198")));
        message.add(new FixPair(6, FixField.AvgPx, new FixValue("41.21")));
        message.add(new FixPair(10, FixField.CheckSum, new FixValue("024")));
        return new FixMessage(0L, FixVersion.FIX_42, MsgType.Unknown, message);
    }

    public FixMessage legalFixMessage7() {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(9, FixField.BodyLength, new FixValue("198")));
        message.add(new FixPair(6, FixField.AvgPx, new FixValue("41.21")));
        message.add(new FixPair(10, FixField.CheckSum, new FixValue("024")));
        return new FixMessage(0L, FixVersion.UNKNOWN, MsgType.Unknown, message);
    }

    public FixMessage createFixMessage(String sendingTime, String sender, String target) {
        List<FixPair> message = new ArrayList<>();
        message.add(new FixPair(52, FixField.SendingTime, new FixValue(sendingTime)));
        message.add(new FixPair(49, FixField.SenderCompID, new FixValue(sender)));
        message.add(new FixPair(56, FixField.TargetCompID, new FixValue(target)));
        return new FixMessage(0L, FixVersion.FIX_50, MsgType.ExecutionReport, message);
    }

    public FixMessage createFixMessage(String sendingTime, String sender, String target, FixPair fixPair) {
        FixMessage message = createFixMessage(sendingTime, sender, target);
        message.getMessageFields().add(fixPair);
        return message;
    }

}
