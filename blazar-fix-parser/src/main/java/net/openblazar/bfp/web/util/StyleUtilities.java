package net.openblazar.bfp.web.util;

import net.openblazar.bfp.fix.data.FixField;
import net.openblazar.bfp.fix.data.field.MsgType;
import net.openblazar.bfp.fix.data.field.OrdStatus;

/**
 * @author Wojciech Zankowski
 */
public class StyleUtilities {

    public static String getStyleForMsgType(MsgType msgType) {
        switch (msgType) {
            case OrderCancelReject:
                return "fieldcolumn fieldcolumn-red";
            case ExecutionReport:
                return "fieldcolumn fieldcolumn-blue";
            default:
                return "fieldcolumn fieldcolumn-grey";
        }
    }

    public static String getStyleForField(FixField fixField) {
        switch (fixField) {
            case MsgType:
                return "fieldcolumn fieldcolumn-blue";
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
            case Rejected:
                return "fieldcolumn fieldcolumn-red";
            default:
                return "fieldcolumn fieldcolumn-grey";
        }
    }

}
