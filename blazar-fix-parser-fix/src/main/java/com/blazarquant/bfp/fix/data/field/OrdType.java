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
public enum OrdType {

    Market("1", "Market"),
    Limit("2", "Limit"),
    Stop("3", "Stop"),
    Stop_Limit("4", "Stop Limit"),
    Market_on_close("5", "Market on close"),
    With_or_without("6", "With or without"),
    Limit_or_better("7", "Limit or better"),
    Limit_with_or_without("8", "Limit with or without"),
    On_basis("9", "On basis"),
    On_close("A", "On close"),
    Limit_on_close("B", "Limit on close"),
    Forex("C", "Forex"),
    Previously_quoted("D", "Previously quoted"),
    Previously_indicated("E", "Previously indicated"),
    Forex_Limit("F", "Forex - Limit"),
    Forex_Swap("G", "Forex - Swap"),
    Forex_Quoted("H", "Forex - Previously Quoted"),
    Funari("I", "Funari"),
    MarketIfTouched("J", "Market If Touched"),
    MarketWithLeftOver("K", "Market With Left Over as Limit "),
    PreviousFundValuation("L", "Previous Fund Valuation Point"),
    NextFundValuation("M", "Next Fund Valuation Point"),
    Pegged("P", "Pegged"),
    CounterOrder("Q", "Counter-order selection"),
    Unknown("", "");

    public final String value;
    public final String description;

    OrdType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (OrdType ordType : values()) {
            if (ordType.getValue().equals(value)) {
                return ordType.getDescription();
            }
        }
        return Unknown.getDescription();
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
