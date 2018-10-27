package pl.zankowski.fixparser.web.util;

import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.web.model.FixEnum;
import pl.zankowski.fixparser.web.model.field.MsgType;
import pl.zankowski.fixparser.web.model.field.OrdStatus;


public class StyleUtilities {

    public static String getStyleForMsgType(FixPairTO fixPair) {
        MsgType msgType = MsgType.getMsgTypeFromValue(fixPair.getFixValue().getValue());
        switch (msgType) {
            case OrderCancelReject:
                return "fieldcolumn fieldcolumn-red";
            case OrderCancelReplaceRequest:
            case OrderCancelRequest:
                return "fieldcolumn fieldcolumn-orange";
            case ExecutionReport:
                return "fieldcolumn fieldcolumn-blue";
            case NewOrderSingle:
                return "fieldcolumn fieldcolumn-green";
            default:
                return "fieldcolumn fieldcolumn-grey";
        }
    }

    public static String getStyleForField(int tag) {
        FixEnum fixField = FixEnum.getFieldFromTag(tag);
        switch (fixField) {
            case AdvSide:
            case AdvTransType:
            case ExecInst:
            case ExecTransType:
            case MsgType:
            case OrdStatus:
            case OrdType:
            case Side:
            case TimeInForce:
            case CxlRejReason:
            case OrdRejReason:
            case ExecType:
                return "fieldcolumn fieldcolumn-dict";
            default:
                return "fieldcolumn fieldcolumn-grey";
        }
    }

    public static String getStyleForOrdStatus(String value) {
        if (value.isEmpty()) {
            return "";
        }

        OrdStatus ordStatus = OrdStatus.getOrdStatusFromValue(value);
        switch (ordStatus) {
            case New:
                return "fieldcolumn fieldcolumn-green";
            case Partially_Filled:
                return "fieldcolumn fieldcolumn-purple-light";
            case Filled:
                return "fieldcolumn fieldcolumn-purple-hard";
            case Rejected:
            case Canceled:
                return "fieldcolumn fieldcolumn-red";
            case Replaced:
                return "fieldcolumn fieldcolumn-dust";
            default:
                return "fieldcolumn fieldcolumn-grey";
        }
    }

}
