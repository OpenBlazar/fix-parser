package pl.zankowski.fixparser.user;

import pl.zankowski.fixparser.user.api.UserSetting;

public class UserSettingTranslator {

    public Object resolveSetting(UserSetting userSetting, String value) {
        switch (userSetting) {
            case DEFAULT_PROVIDER:
//                return FixDefinitionProvidersFileUtility.resolveProviderFileName(value);
            case STORE_MESSAGES:
                return Boolean.valueOf(value);
            default:
                throw new IllegalArgumentException("Failed to resolve setting. Setting " + userSetting + " not supported.");
        }
    }

    public String translateSetting(UserSetting userSetting, Object value) {
        switch (userSetting) {
            case DEFAULT_PROVIDER:
//                return FixDefinitionProvidersFileUtility.createProviderFileName((ProviderDescriptor) value);
            case STORE_MESSAGES:
                return ((Boolean) value).toString();
            default:
                throw new IllegalArgumentException("Failed to translate setting object. Setting " + userSetting + " not supported.");
        }
    }

}
