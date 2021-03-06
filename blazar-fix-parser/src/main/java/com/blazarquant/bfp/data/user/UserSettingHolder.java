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

import java.util.Objects;

/**
 * @author Wojciech Zankowski
 */
public class UserSettingHolder {

    private final UserSetting userSetting;
    private final String settingValue;

    public UserSettingHolder(UserSetting userSetting, String settingValue) {
        Objects.requireNonNull(userSetting);
        Objects.requireNonNull(settingValue);

        this.userSetting = userSetting;
        this.settingValue = settingValue;
    }

    public UserSetting getUserSetting() {
        return userSetting;
    }

    public String getSettingValue() {
        return settingValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSettingHolder that = (UserSettingHolder) o;

        if (userSetting != that.userSetting) return false;
        return settingValue != null ? settingValue.equals(that.settingValue) : that.settingValue == null;

    }

    @Override
    public int hashCode() {
        int result = userSetting != null ? userSetting.hashCode() : 0;
        result = 31 * result + (settingValue != null ? settingValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserSettingHolderTypeHandler{" +
                "userSetting=" + userSetting +
                ", settingValue='" + settingValue + '\'' +
                '}';
    }
}
