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
