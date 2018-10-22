package pl.zankowski.fixparser.user;

import pl.zankowski.fixparser.user.entity.UserID;
import pl.zankowski.fixparser.user.entity.UserSetting;
import pl.zankowski.fixparser.user.entity.UserSettingHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSettingsCache {

    private final UserDAO userDAO;

    private final Map<UserID, Map<UserSetting, Object>> defaultSettings = new HashMap<>();
    private final UserSettingTranslator userSettingTranslator = new UserSettingTranslator();

    public UserSettingsCache(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void loadParameters(UserID userID) {
        List<UserSettingHolder> userSettingHolders = userDAO.findParameters(userID);
        Map<UserSetting, Object> userSettingsActual = defaultSettings.get(userID);
        if (userSettingsActual == null) {
            userSettingsActual = new HashMap<>();
        }
        for (UserSettingHolder holder : userSettingHolders) {
            userSettingsActual.put(
                    holder.getUserSetting(),
                    userSettingTranslator.resolveSetting(holder.getUserSetting(), holder.getSettingValue())
            );
        }
        defaultSettings.put(userID, userSettingsActual);
    }

    public void createDefaultParameters(UserID userID) {
//        userDAO.saveParameter(userID, UserSetting.DEFAULT_PROVIDER, userSettingTranslator.translateSetting(UserSetting.DEFAULT_PROVIDER, DefaultFixDefinitionProvider.DESCRIPTOR));
        userDAO.saveParameter(userID, UserSetting.STORE_MESSAGES, userSettingTranslator.translateSetting(UserSetting.STORE_MESSAGES, Boolean.TRUE));
    }

    public void setParameter(UserID userID, UserSetting userSetting, Object value) {
        String settingValue = userSettingTranslator.translateSetting(userSetting, value);
        Map<UserSetting, Object> userSettings = defaultSettings.get(userID);
        if (userSettings == null) {
            userSettings = new HashMap<>();
        }
        userSettings.put(userSetting, value);
        defaultSettings.put(userID, userSettings);
        userDAO.saveParameter(userID, userSetting, settingValue);
    }

    public String getParameter(UserID userID, UserSetting userSetting) {
        return (String) getObject(userID, userSetting);
    }

    public Object getObject(UserID userID, UserSetting userSetting) {
        Map<UserSetting, Object> userSettings = defaultSettings.get(userID);
        if (userSettings == null) {
            return null;
        }
        return userSettings.get(userSetting);
    }

    public Boolean getBoolean(UserID userID, UserSetting userSetting) {
        return (Boolean) getObject(userID, userSetting);
    }

}
