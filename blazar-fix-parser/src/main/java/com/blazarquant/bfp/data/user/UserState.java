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
package com.blazarquant.bfp.data.user;

import java.io.Serializable;

/**
 * @author Wojciech Zankowski
 */
public enum UserState implements Serializable {

    ACTIVE(1),
    INACTIVE(0),
    UNKNOWN(-1);

    private final int state;

    UserState(int state) {
        this.state = state;
    }

    public static UserState getUserStateFromCode(int code) {
        for (UserState userState : values()) {
            if (userState.getState() == code) {
                return userState;
            }
        }
        return UNKNOWN;
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "UserState{" +
                "state=" + state +
                '}';
    }
}
