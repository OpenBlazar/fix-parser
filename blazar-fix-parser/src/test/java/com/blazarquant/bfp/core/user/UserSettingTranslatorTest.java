package com.blazarquant.bfp.core.user;

import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class UserSettingTranslatorTest {

    private UserSettingTranslator translator;

    private final String expectedProviderValue = "QF#PROVIDER";
    private final String expectedStoreValue = "false";
    private final ProviderDescriptor expectedProviderDescriptor = new ProviderDescriptor("PROVIDER", XMLLoaderType.QUICKFIX_LOADER);
    private final Boolean expectedStore = Boolean.FALSE;

    @Before
    public void setUp() {
        translator = new UserSettingTranslator();
    }

    @Test
    public void testResolveSetting() {
        ProviderDescriptor providerDescriptor = (ProviderDescriptor) translator.resolveSetting(UserSetting.DEFAULT_PROVIDER, expectedProviderValue);
        assertEquals(expectedProviderDescriptor.getProviderName(), providerDescriptor.getProviderName());
        assertEquals(expectedProviderDescriptor.getLoaderType(), providerDescriptor.getLoaderType());

        Boolean store = (Boolean) translator.resolveSetting(UserSetting.STORE_MESSAGES, expectedStoreValue);
        assertEquals(expectedStore, store);
    }

    @Test
    public void testTranslateSetting() {
        String providerValue = translator.translateSetting(UserSetting.DEFAULT_PROVIDER, expectedProviderDescriptor);
        assertEquals(expectedProviderValue, providerValue);

        String storeValue = translator.translateSetting(UserSetting.STORE_MESSAGES, expectedStore);
        assertEquals(expectedStoreValue, storeValue);
    }

}
