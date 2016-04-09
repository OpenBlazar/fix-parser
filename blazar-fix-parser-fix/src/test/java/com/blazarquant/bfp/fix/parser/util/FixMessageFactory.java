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
        List<FixPair> firstMsgFields = new ArrayList<>();
        firstMsgFields.add(new FixPair(FixField.BeginString, new FixValue("FIX.4.2")));
        firstMsgFields.add(new FixPair(FixField.BodyLength, new FixValue("198")));
        firstMsgFields.add(new FixPair(FixField.MsgType, new FixValue("8", "ExecutionReport")));
        firstMsgFields.add(new FixPair(FixField.AvgPx, new FixValue("41.21")));
        firstMsgFields.add(new FixPair(FixField.CheckSum, new FixValue("024")));
        FixMessage firstMessage = new FixMessage(0L, FixVersion.FIX_42, MsgType.ExecutionReport, firstMsgFields);

        List<FixPair> secondMsgFields = new ArrayList<>();
        secondMsgFields.add(new FixPair(FixField.BeginString, new FixValue("FIX.4.2")));
        secondMsgFields.add(new FixPair(FixField.BodyLength, new FixValue("204")));
        secondMsgFields.add(new FixPair(FixField.MsgType, new FixValue("8", "ExecutionReport")));
        secondMsgFields.add(new FixPair(FixField.AvgPx, new FixValue("102.75")));
        secondMsgFields.add(new FixPair(FixField.ClOrdID, new FixValue("38400195")));
        secondMsgFields.add(new FixPair(FixField.CheckSum, new FixValue("152")));
        FixMessage secondMessage = new FixMessage(1L, FixVersion.FIX_42, MsgType.ExecutionReport, secondMsgFields);

        List<FixPair> thirdMsgFields = new ArrayList<>();
        thirdMsgFields.add(new FixPair(FixField.BeginString,  new FixValue("FIX.4.2")));
        thirdMsgFields.add(new FixPair(FixField.BodyLength,  new FixValue("198")));
        thirdMsgFields.add(new FixPair(FixField.MsgType,  new FixValue("8", "ExecutionReport")));
        thirdMsgFields.add(new FixPair(FixField.AvgPx,  new FixValue("41.21")));
        thirdMsgFields.add(new FixPair(FixField.ClOrdID,  new FixValue("3840019")));
        thirdMsgFields.add(new FixPair(FixField.CumQty,  new FixValue("102")));
        thirdMsgFields.add(new FixPair(FixField.CheckSum,  new FixValue("024")));
        FixMessage thirdMessage = new FixMessage(2L, FixVersion.FIX_42, MsgType.ExecutionReport, thirdMsgFields);

        List<FixMessage> messages = new ArrayList<>();
        messages.add(firstMessage);
        messages.add(secondMessage);
        messages.add(thirdMessage);
        return messages;
    }

    public FixMessage createFixMessage(String sendingTime, String sender, String target) {
       List<FixPair> msgFields = new ArrayList<>();
        msgFields.add(new FixPair(FixField.SendingTime, new FixValue(sendingTime)));
        msgFields.add(new FixPair(FixField.SenderCompID, new FixValue(sender)));
        msgFields.add(new FixPair(FixField.TargetCompID, new FixValue(target)));
        return new FixMessage(0L, FixVersion.FIX_50, MsgType.ExecutionReport, msgFields);
    }

}
