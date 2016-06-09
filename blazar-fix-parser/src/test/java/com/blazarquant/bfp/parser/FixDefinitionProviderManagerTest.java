package com.blazarquant.bfp.parser;

import com.blazarquant.bfp.core.parser.FixDefinitionProviderManager;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProviderManagerTest {

    private FixDefinitionProviderManager providerManager;

    @Before
    public void setUp() throws Exception {
        providerManager = new FixDefinitionProviderManager();
    }

    @Test
    public void testDefaultProvider() {
        FixDefinitionProvider definitionProvider = providerManager.getDefaultDefinitionProvider();
        assertTrue(definitionProvider instanceof DefaultFixDefinitionProvider);
    }

    @Test
    public void testAddAndReceiveDefinitionProvidersForOneUser() {
        final UserID userID = new UserID(9);
        final ProviderDescriptor providerDescriptor_1 = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider_1 = mock(FixDefinitionProvider.class);

        final ProviderDescriptor providerDescriptor_2 = new ProviderDescriptor("NAME2", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider_2 = mock(FixDefinitionProvider.class);

        providerManager.addDefinitionProvider(userID, providerDescriptor_1, definitionProvider_1);
        providerManager.addDefinitionProvider(userID, providerDescriptor_2, definitionProvider_2);

        Map<ProviderDescriptor, FixDefinitionProvider> providers = providerManager.getDefinitionProviders(userID);
        assertEquals(2, providers.size());

        FixDefinitionProvider actualDefinitionProvider_1 = providers.get(providerDescriptor_1);
        assertEquals(definitionProvider_1, actualDefinitionProvider_1);

        FixDefinitionProvider actualDefinitionProvider_2 = providers.get(providerDescriptor_2);
        assertEquals(definitionProvider_2, actualDefinitionProvider_2);

        Set<ProviderDescriptor> providerDescriptors = providerManager.getProviderDescriptors(userID);
        assertEquals(2, providerDescriptors.size());
        assertTrue(providerDescriptors.contains(providerDescriptor_1));
        assertTrue(providerDescriptors.contains(providerDescriptor_2));
    }

    @Test
    public void testAddAndReceiveDefinitionProvidersForMultipleUsers() {
        final UserID userID_1 = new UserID(0);
        final ProviderDescriptor providerDescriptor_1 = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider_1 = mock(FixDefinitionProvider.class);

        final UserID userID_2 = new UserID(1);
        final ProviderDescriptor providerDescriptor_2 = new ProviderDescriptor("NAME2", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider_2 = mock(FixDefinitionProvider.class);

        providerManager.addDefinitionProvider(userID_1, providerDescriptor_1, definitionProvider_1);
        providerManager.addDefinitionProvider(userID_2, providerDescriptor_2, definitionProvider_2);

        // First user
        Map<ProviderDescriptor, FixDefinitionProvider> providers = providerManager.getDefinitionProviders(userID_1);
        assertEquals(1, providers.size());

        FixDefinitionProvider actualDefinitionProvider_1 = providers.get(providerDescriptor_1);
        assertEquals(definitionProvider_1, actualDefinitionProvider_1);

        Set<ProviderDescriptor> providerDescriptors = providerManager.getProviderDescriptors(userID_1);
        assertEquals(1, providerDescriptors.size());
        assertTrue(providerDescriptors.contains(providerDescriptor_1));

        // Second user
        providers = providerManager.getDefinitionProviders(userID_2);
        assertEquals(1, providers.size());

        FixDefinitionProvider actualDefinitionProvider_2 = providers.get(providerDescriptor_2);
        assertEquals(definitionProvider_2, actualDefinitionProvider_2);

        providerDescriptors = providerManager.getProviderDescriptors(userID_2);
        assertEquals(1, providerDescriptors.size());
        assertTrue(providerDescriptors.contains(providerDescriptor_2));
    }

    @Test
    public void testAddAndRemoveDefinitionProviders() {
        final UserID userID = new UserID(9);
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider = mock(FixDefinitionProvider.class);

        providerManager.addDefinitionProvider(userID, providerDescriptor, definitionProvider);

        // Check if added
        Map<ProviderDescriptor, FixDefinitionProvider> providers = providerManager.getDefinitionProviders(userID);
        assertEquals(1, providers.size());

        FixDefinitionProvider actualDefinitionProvider = providers.get(providerDescriptor);
        assertEquals(definitionProvider, actualDefinitionProvider);

        Set<ProviderDescriptor> providerDescriptors = providerManager.getProviderDescriptors(userID);
        assertEquals(1, providerDescriptors.size());
        assertTrue(providerDescriptors.contains(providerDescriptor));

        // Remove
        providerManager.removeDefinitionProvider(userID, providerDescriptor);

        providers = providerManager.getDefinitionProviders(userID);
        assertEquals(0, providers.size());
    }

    @Test
    public void testProDefinitionProvidersManager() {
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider = mock(FixDefinitionProvider.class);

        Map<ProviderDescriptor, FixDefinitionProvider> providers = new HashMap<>();
        providers.put(providerDescriptor, definitionProvider);

        providerManager.addProDefinitionProviders(providers);

        assertEquals(providers, providerManager.getProDefinitionProviders());
    }

}
