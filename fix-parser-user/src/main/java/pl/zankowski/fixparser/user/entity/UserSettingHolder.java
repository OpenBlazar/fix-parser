package pl.zankowski.fixparser.user.entity;

import java.util.Objects;

public class UserSettingHolder {

    private final UserSetting userSetting;
    private final String settingValue;

    public UserSettingHolder(final UserSetting userSetting, final String settingValue) {
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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserSettingHolder that = (UserSettingHolder) o;
        return userSetting == that.userSetting &&
                Objects.equals(settingValue, that.settingValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userSetting, settingValue);
    }

    @Override
    public String toString() {
        return "UserSettingHolder{" +
                "userSetting=" + userSetting +
                ", settingValue='" + settingValue + '\'' +
                '}';
    }

}
