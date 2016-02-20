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
public class FixMessageFactory {

    public List<FixMessage> prepareFixMessagesForLegalFixLong() {
        Map<FixField, String> firstMsgFields = new HashMap<>();
        firstMsgFields.put(FixField.BeginString, "FIX.4.2");
        firstMsgFields.put(FixField.BodyLength, "198");
        firstMsgFields.put(FixField.MsgType, "8");
        firstMsgFields.put(FixField.AvgPx, "41.21");
        firstMsgFields.put(FixField.CheckSum, "024");
        FixMessage firstMessage = new FixMessage(FixVersion.FIX_42, FixMessageType.EXECUTION_REPORT, firstMsgFields);

        Map<FixField, String> secondMsgFields = new HashMap<>();
        secondMsgFields.put(FixField.BeginString, "FIX.4.2");
        secondMsgFields.put(FixField.BodyLength, "204");
        secondMsgFields.put(FixField.MsgType, "8");
        secondMsgFields.put(FixField.AvgPx, "102.75");
        secondMsgFields.put(FixField.ClOrdID, "38400195");
        secondMsgFields.put(FixField.CheckSum, "152");
        FixMessage secondMessage = new FixMessage(FixVersion.FIX_42, FixMessageType.EXECUTION_REPORT, secondMsgFields);

        Map<FixField, String> thirdMsgFields = new HashMap<>();
        thirdMsgFields.put(FixField.BeginString, "FIX.4.2");
        thirdMsgFields.put(FixField.BodyLength, "198");
        thirdMsgFields.put(FixField.MsgType, "8");
        thirdMsgFields.put(FixField.AvgPx, "41.21");
        thirdMsgFields.put(FixField.ClOrdID, "3840019");
        thirdMsgFields.put(FixField.CheckSum, "024");
        thirdMsgFields.put(FixField.CumQty, "102");
        FixMessage thirdMessage = new FixMessage(FixVersion.FIX_42, FixMessageType.EXECUTION_REPORT, thirdMsgFields);

        List<FixMessage> messages = new ArrayList<>();
        messages.add(firstMessage);
        messages.add(secondMessage);
        messages.add(thirdMessage);
        return messages;
    }

}
