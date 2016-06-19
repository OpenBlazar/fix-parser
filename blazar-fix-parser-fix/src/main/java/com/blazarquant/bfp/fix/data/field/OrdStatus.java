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
package com.blazarquant.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum OrdStatus {

    New("0", "New"),
    Partially_Filled("1", "Partially Filled"),
    Filled("2", "Filled"),
    Done_for_day("3", "Done for day"),
    Canceled("4", "Canceled"),
    Replaced("5", "Replaced"),
    Pending_CancelReplace("6", "Pending Cancel/Replace"),
    Stopped("7", "Stopped"),
    Rejected("8", "Rejected"),
    Suspended("9", "Suspended"),
    Pending_New("A", "Pending New"),
    Calculated("B", "Calculated"),
    Expired("C", "Expired"),
    Bidding("D", "Accepted for bidding"),
    Pending_Replace("E", "Pending Replace"),
    Unknown("", "");

    private final String value;
    private final String description;

    OrdStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionFromValue(String value) {
        return getOrdStatusFromValue(value).getDescription();
    }

    public static OrdStatus getOrdStatusFromValue(String value) {
        for (OrdStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return Unknown;
    }
}
