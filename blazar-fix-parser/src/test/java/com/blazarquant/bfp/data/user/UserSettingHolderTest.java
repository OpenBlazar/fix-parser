package com.blazarquant.bfp.data.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class UserSettingHolderTest {

    private final UserSetting USER_SETTING = UserSetting.DEFAULT_PROVIDER;
    private final String SETTING_VALUE = "QF#FIX42";

    @Test
    public void testNullParameter() {
        try {
            UserSettingHolder userSettingHolder = new UserSettingHolder(null, SETTING_VALUE);
            fail("Test failed. User setting cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserSettingHolder userSettingHolder = new UserSettingHolder(USER_SETTING, null);
            fail("Test failed. Setting value cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
    }

    @Test
    public void testObjectBehaviour() {
        UserSettingHolder userSettingHolder = new UserSettingHolder(USER_SETTING, SETTING_VALUE);
        assertEquals(USER_SETTING, userSettingHolder.getUserSetting());
        assertEquals(SETTING_VALUE, userSettingHolder.getSettingValue());
    }

}
