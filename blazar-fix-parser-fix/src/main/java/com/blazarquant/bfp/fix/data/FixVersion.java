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
package com.blazarquant.bfp.fix.data;

/**
 * @author Wojciech Zankowski
 */
public enum FixVersion {

    FIX_40("FIX.4.0"),
    FIX_41("FIX.4.1"),
    FIX_42("FIX.4.2"),
    FIX_43("FIX.4.3"),
    FIX_44("FIX.4.4"),
    FIX_50("FIXT.1.1"),
    UNKNOWN("");

    private String code;

    FixVersion(String code) {
        this.code = code;
    }

    public static FixVersion getFixVersionFromCode(String code) {
        for (FixVersion version : values()) {
            if (version.getCode().equals(code)) {
                return version;
            }
        }
        return UNKNOWN;
    }

    public String getCode() {
        return code;
    }

}
