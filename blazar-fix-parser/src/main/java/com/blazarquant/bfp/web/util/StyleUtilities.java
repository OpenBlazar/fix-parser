/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.web.util;

import com.blazarquant.bfp.fix.data.FixEnum;
import com.blazarquant.bfp.fix.data.FixPair;
import com.blazarquant.bfp.fix.data.field.MsgType;
import com.blazarquant.bfp.fix.data.field.OrdStatus;

/**
 * @author Wojciech Zankowski
 */
public class StyleUtilities {

    public static String getStyleForMsgType(FixPair fixPair) {
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
