package net.openblazar.bfp.core.parser.util;

import net.openblazar.bfp.data.fix.*;
import net.openblazar.bfp.data.fix.field.MsgType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageFactory {

    public List<FixMessage> prepareFixMessagesForLegalFixLong() {
        Map<FixField, FixValue> firstMsgFields = new HashMap<>();
        firstMsgFields.put(FixField.BeginString, new FixValue("FIX.4.2"));
        firstMsgFields.put(FixField.BodyLength, new FixValue("198"));
        firstMsgFields.put(FixField.MsgType, new FixValue("8", "ExecutionReport"));
        firstMsgFields.put(FixField.AvgPx, new FixValue("41.21"));
        firstMsgFields.put(FixField.CheckSum, new FixValue("024"));
        FixMessage firstMessage = new FixMessage(0L, FixVersion.FIX_42, MsgType.ExecutionReport, firstMsgFields);

        Map<FixField, FixValue> secondMsgFields = new HashMap<>();
        secondMsgFields.put(FixField.BeginString, new FixValue("FIX.4.2"));
        secondMsgFields.put(FixField.BodyLength, new FixValue("204"));
        secondMsgFields.put(FixField.MsgType, new FixValue("8", "ExecutionReport"));
        secondMsgFields.put(FixField.AvgPx, new FixValue("102.75"));
        secondMsgFields.put(FixField.ClOrdID, new FixValue("38400195"));
        secondMsgFields.put(FixField.CheckSum, new FixValue("152"));
        FixMessage secondMessage = new FixMessage(1L, FixVersion.FIX_42, MsgType.ExecutionReport, secondMsgFields);

        Map<FixField, FixValue> thirdMsgFields = new HashMap<>();
        thirdMsgFields.put(FixField.BeginString,  new FixValue("FIX.4.2"));
        thirdMsgFields.put(FixField.BodyLength,  new FixValue("198"));
        thirdMsgFields.put(FixField.MsgType,  new FixValue("8", "ExecutionReport"));
        thirdMsgFields.put(FixField.AvgPx,  new FixValue("41.21"));
        thirdMsgFields.put(FixField.ClOrdID,  new FixValue("3840019"));
        thirdMsgFields.put(FixField.CheckSum,  new FixValue("024"));
        thirdMsgFields.put(FixField.CumQty,  new FixValue("102"));
        FixMessage thirdMessage = new FixMessage(2L, FixVersion.FIX_42, MsgType.ExecutionReport, thirdMsgFields);

        List<FixMessage> messages = new ArrayList<>();
        messages.add(firstMessage);
        messages.add(secondMessage);
        messages.add(thirdMessage);
        return messages;
    }

}
