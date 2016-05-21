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
            default:
                throw new IllegalArgumentException("Failed to resolve setting. Setting " + userSetting + " not supported.");
        }
    }

    public String translateSetting(UserSetting userSetting, Object value) {
        switch (userSetting) {
            case DEFAULT_PROVIDER:
                return FixDefinitionProvidersFileUtility.createProviderFileName((ProviderDescriptor) value);
            default:
                throw new IllegalArgumentException("Failed to translate setting object. Setting " + userSetting + " not supported.");
        }
    }

}
