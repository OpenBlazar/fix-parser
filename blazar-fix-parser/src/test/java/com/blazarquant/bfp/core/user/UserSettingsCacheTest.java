package com.blazarquant.bfp.core.user;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.data.user.UserSettingHolder;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class UserSettingsCacheTest {

    private UserDAO userDAO;
    private UserSettingsCache userSettingsCache;

    @Before
    public void setUp() {
        userDAO = mock(UserDAO.class);
        userSettingsCache = new UserSettingsCache(userDAO);
    }

    @Test
    public void testDefaultParameters() {
        final UserID userID = new UserID(9);
        userSettingsCache.createDefaultParameters(userID);

        final ArgumentCaptor<UserSetting> userSettingCaptor = ArgumentCaptor.forClass(UserSetting.class);
        final ArgumentCaptor<String> valueCaptor = ArgumentCaptor.forClass(String.class);
        verify(userDAO, times(2)).saveParameter(eq(userID), userSettingCaptor.capture(), valueCaptor.capture());

        List<UserSetting> userSettingList = userSettingCaptor.getAllValues();
        List<String> values = valueCaptor.getAllValues();

        assertEquals(UserSetting.DEFAULT_PROVIDER, userSettingList.get(0));
        assertEquals("QF#Default", values.get(0));
        assertEquals(UserSetting.STORE_MESSAGES, userSettingList.get(1));
        assertEquals("true", values.get(1));
    }

    @Test
    public void testNotLoadedParameters() {
        final UserID userID = new UserID(0);
        assertNull(userSettingsCache.getParameter(userID, UserSetting.DEFAULT_PROVIDER));
        assertNull(userSettingsCache.getParameter(userID, UserSetting.STORE_MESSAGES));
    }

    @Test
    public void testCachedParameters() {
        final UserID userID = new UserID(5);

        when(userDAO.findParameters(userID)).thenReturn(Arrays.asList(
                new UserSettingHolder(UserSetting.DEFAULT_PROVIDER, "QF#Default"),
                new UserSettingHolder(UserSetting.STORE_MESSAGES, "true"
                )));

        userSettingsCache.createDefaultParameters(userID);
        userSettingsCache.loadParameters(userID);

        Boolean value = userSettingsCache.getBoolean(userID, UserSetting.STORE_MESSAGES);
        assertEquals(Boolean.TRUE, value);

        ProviderDescriptor providerDescriptor = (ProviderDescriptor) userSettingsCache.getObject(userID, UserSetting.DEFAULT_PROVIDER);
        assertEquals(XMLLoaderType.QUICKFIX_LOADER, providerDescriptor.getLoaderType());
        assertEquals(DefaultFixDefinitionProvider.NAME, providerDescriptor.getProviderName());
    }

    @Test
    public void testSaveParameters() {
        final UserID userID = new UserID(0);
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        userSettingsCache.setParameter(userID, UserSetting.DEFAULT_PROVIDER, providerDescriptor);

        final ArgumentCaptor<UserSetting> userSettingCaptor = ArgumentCaptor.forClass(UserSetting.class);
        final ArgumentCaptor<String> valuesCaptor = ArgumentCaptor.forClass(String.class);
        verify(userDAO).saveParameter(eq(userID), userSettingCaptor.capture(), valuesCaptor.capture());

        assertEquals(UserSetting.DEFAULT_PROVIDER, userSettingCaptor.getValue());
        assertEquals("QF#NAME", valuesCaptor.getValue());

        ProviderDescriptor actualProviderDescriptor = (ProviderDescriptor) userSettingsCache.getObject(userID, UserSetting.DEFAULT_PROVIDER);
        assertEquals(providerDescriptor, actualProviderDescriptor);
    }

}
