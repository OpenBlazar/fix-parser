package pl.zankowski.fixparser.messages.fix;

import com.google.common.collect.Lists;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.api.FixVersion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FixMessageFactory {

    public static final FixPairTO EXECUTION_REPORT = new FixPairTO(35,
            new FixFieldTO(35, "MsgType"), new FixValueTO("8", "EXECUTIONREPORT"));

    public static FixMessageTO createFix42Message(long id, String value, String description) {
        FixPairTO msgType = new FixPairTO(35, new FixFieldTO(35, "MsgType"), new FixValueTO(value, description));
        List<FixPairTO> fixPairList = Arrays.asList(
                new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.2", "")),
                new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("14", "")),
                msgType,
                new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("213", ""))
        );
        return new FixMessageTO(id, FixVersion.FIX_42, msgType, fixPairList);
    }

    public List<FixMessageTO> prepareFixMessagesForLegalFixLong() {
        List<FixMessageTO> messages = Lists.newArrayList();
        messages.add(legalFixMessageLong1());
        messages.add(legalFixMessageLong2());
        messages.add(legalFixMessageLong3());
        return messages;
    }

    public FixMessageTO legalFixMessageLong1() {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.2", "")));
        message.add(new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("198", "")));
        message.add(new FixPairTO(35, new FixFieldTO(35, "MsgType"), new FixValueTO("8", "EXECUTIONREPORT")));
        message.add(new FixPairTO(6, new FixFieldTO(6, "AvgPx"), new FixValueTO("41.21", "")));
        message.add(new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("024", "")));
        return new FixMessageTO(0L, FixVersion.FIX_42, EXECUTION_REPORT, message);
    }

    public FixMessageTO legalFixMessageLong2() {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.2", "")));
        message.add(new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("204", "")));
        message.add(new FixPairTO(35, new FixFieldTO(35, "MsgType"), new FixValueTO("8", "EXECUTIONREPORT")));
        message.add(new FixPairTO(6, new FixFieldTO(6, "AvgPx"), new FixValueTO("102.75", "")));
        message.add(new FixPairTO(11, new FixFieldTO(11, "ClOrdID"), new FixValueTO("38400195", "")));
        message.add(new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("152", "")));
        return new FixMessageTO(1L, FixVersion.FIX_42, EXECUTION_REPORT, message);
    }

    public FixMessageTO legalFixMessageLong3() {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.2", "")));
        message.add(new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("198", "")));
        message.add(new FixPairTO(35, new FixFieldTO(35, "MsgType"), new FixValueTO("8", "EXECUTIONREPORT")));
        message.add(new FixPairTO(6, new FixFieldTO(6, "AvgPx"), new FixValueTO("41.21", "")));
        message.add(new FixPairTO(11, new FixFieldTO(11, "ClOrdID"), new FixValueTO("3840019", "")));
        message.add(new FixPairTO(14, new FixFieldTO(14, "CumQty"), new FixValueTO("102", "")));
        message.add(new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("024", "")));
        return new FixMessageTO(2L, FixVersion.FIX_42, EXECUTION_REPORT, message);
    }

    public FixMessageTO legalFixMessage4() {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.2", "")));
        message.add(new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("197", "")));
        message.add(new FixPairTO(35, new FixFieldTO(35, "MsgType"), new FixValueTO("8", "EXECUTIONREPORT")));
        message.add(new FixPairTO(6, new FixFieldTO(6, "AvgPx"), new FixValueTO("0.0", "")));
        message.add(new FixPairTO(11, new FixFieldTO(11, "ClOrdID"), new FixValueTO("373009", "")));
        message.add(new FixPairTO(14, new FixFieldTO(14, "CumQty"), new FixValueTO("0", "")));
        message.add(new FixPairTO(17, new FixFieldTO(17, "ExecID"), new FixValueTO("373009", "")));
        return new FixMessageTO(0L, FixVersion.FIX_42, EXECUTION_REPORT, message);
    }

    public FixMessageTO legalFixMessage6() {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.2", "")));
        message.add(new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("198", "")));
        message.add(new FixPairTO(6, new FixFieldTO(6, "AvgPx"), new FixValueTO("41.21", "")));
        message.add(new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("024", "")));
        return new FixMessageTO(0L, FixVersion.FIX_42, FixPairTO.UNKNOWN, message);
    }

    public FixMessageTO legalFixMessage7() {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("198", "")));
        message.add(new FixPairTO(6, new FixFieldTO(6, "AvgPx"), new FixValueTO("41.21", "")));
        message.add(new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("024", "")));
        return new FixMessageTO(0L, FixVersion.UNKNOWN, FixPairTO.UNKNOWN, message);
    }

    public FixMessageTO createFixMessage(String sendingTime, String sender, String target) {
        List<FixPairTO> message = new ArrayList<>();
        message.add(new FixPairTO(52, new FixFieldTO(52, "SendingTime"), new FixValueTO(sendingTime, "")));
        message.add(new FixPairTO(49, new FixFieldTO(49, "SenderCompID"), new FixValueTO(sender, "")));
        message.add(new FixPairTO(56, new FixFieldTO(56, "TargetCompID"), new FixValueTO(target, "")));
        return new FixMessageTO(0L, FixVersion.FIX_50, EXECUTION_REPORT, message);
    }

    public FixMessageTO createFixMessage(String sendingTime, String sender, String target, FixPairTO fixPair) {
        FixMessageTO message = createFixMessage(sendingTime, sender, target);
        message.getMessageFields().add(fixPair);
        return message;
    }

}
