package com.blazarquant.bfp.web.util;

import com.blazarquant.bfp.fix.data.FixField;
import com.blazarquant.bfp.fix.data.field.OrdStatus;
import com.blazarquant.bfp.fix.data.field.MsgType;

/**
 * @author Wojciech Zankowski
 */
public class StyleUtilities {

    public static String getStyleForMsgType(MsgType msgType) {
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

    public static String getStyleForField(FixField fixField) {
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
