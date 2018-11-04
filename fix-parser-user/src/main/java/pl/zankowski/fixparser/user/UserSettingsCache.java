package pl.zankowski.fixparser.user;

import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.api.UserSetting;
import pl.zankowski.fixparser.user.entity.UserSettingHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSettingsCache {

    private final UserDAO userDAO;

    private final Map<UserId, Map<UserSetting, Object>> defaultSettings = new HashMap<>();
    private final UserSettingTranslator userSettingTranslator = new UserSettingTranslator();

    public UserSettingsCache(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void loadParameters(UserId userId) {
        List<UserSettingHolder> userSettingHolders = userDAO.findParameters(userId);
        Map<UserSetting, Object> userSettingsActual = defaultSettings.get(userId);
        if (userSettingsActual == null) {
            userSettingsActual = new HashMap<>();
        }
        for (UserSettingHolder holder : userSettingHolders) {
            userSettingsActual.put(
                    holder.getUserSetting(),
                    userSettingTranslator.resolveSetting(holder.getUserSetting(), holder.getSettingValue())
            );
        }
        defaultSettings.put(userId, userSettingsActual);
    }

    public void createDefaultParameters(UserId userId) {
//        userDAO.saveParameter(userId, UserSetting.DEFAULT_PROVIDER, userSettingTranslator.translateSetting(UserSetting.DEFAULT_PROVIDER, DefaultFixDefinitionProvider.DESCRIPTOR));
        userDAO.saveParameter(userId, UserSetting.STORE_MESSAGES, userSettingTranslator.translateSetting(UserSetting.STORE_MESSAGES, Boolean.TRUE));
    }

    public void setParameter(UserId userId, UserSetting userSetting, Object value) {
        String settingValue = userSettingTranslator.translateSetting(userSetting, value);
        Map<UserSetting, Object> userSettings = defaultSettings.get(userId);
        if (userSettings == null) {
            userSettings = new HashMap<>();
        }
        userSettings.put(userSetting, value);
        defaultSettings.put(userId, userSettings);
        userDAO.saveParameter(userId, userSetting, settingValue);
    }

    public String getParameter(UserId userId, UserSetting userSetting) {
        return (String) getObject(userId, userSetting);
    }

    public Object getObject(UserId userId, UserSetting userSetting) {
        Map<UserSetting, Object> userSettings = defaultSettings.get(userId);
        if (userSettings == null) {
            return null;
        }
        return userSettings.get(userSetting);
    }

    public Boolean getBoolean(UserId userId, UserSetting userSetting) {
        return (Boolean) getObject(userId, userSetting);
    }

}
