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
package com.blazarquant.bfp.core.user;

import com.blazarquant.bfp.core.parser.FixDefinitionProvidersFileUtility;
import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;

/**
 * @author Wojciech Zankowski
 */
public class UserSettingTranslator {

    public Object resolveSetting(UserSetting userSetting, String value) {
        switch (userSetting) {
            case DEFAULT_PROVIDER:
                return FixDefinitionProvidersFileUtility.resolveProviderFileName(value);
            case STORE_MESSAGES:
                return Boolean.valueOf(value);
            default:
                throw new IllegalArgumentException("Failed to resolve setting. Setting " + userSetting + " not supported.");
        }
    }

    public String translateSetting(UserSetting userSetting, Object value) {
        switch (userSetting) {
            case DEFAULT_PROVIDER:
                return FixDefinitionProvidersFileUtility.createProviderFileName((ProviderDescriptor) value);
            case STORE_MESSAGES:
                return ((Boolean) value).toString();
            default:
                throw new IllegalArgumentException("Failed to translate setting object. Setting " + userSetting + " not supported.");
        }
    }

}
