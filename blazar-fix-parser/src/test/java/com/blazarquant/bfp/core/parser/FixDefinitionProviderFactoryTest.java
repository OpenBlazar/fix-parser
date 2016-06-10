package com.blazarquant.bfp.core.parser;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.fix.parser.definition.DefaultFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProviderFactoryTest {

    private FixDefinitionProviderFactory definitionProviderFactory;
    private FixDefinitionProviderManager definitionProviderManager;

    @Before
    public void setUp() throws Exception {
        definitionProviderManager = new FixDefinitionProviderManager();
        definitionProviderFactory = new FixDefinitionProviderFactory(definitionProviderManager);
    }

    @Test
    public void testDefaultProvder() {
        FixDefinitionProvider definitionProvider = definitionProviderFactory.getDefinitionProvider();
        assertTrue(definitionProvider instanceof DefaultFixDefinitionProvider);
    }

    @Test
    public void testProDefinitionProvider() {
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider = mock(FixDefinitionProvider.class);

        Map<ProviderDescriptor, FixDefinitionProvider> providers = new HashMap<>();
        providers.put(providerDescriptor, definitionProvider);
        definitionProviderManager.addProDefinitionProviders(providers);

        FixDefinitionProvider actualDefinitionProvider = definitionProviderFactory.getProDefinitionProvider(null);
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);

        actualDefinitionProvider = definitionProviderFactory.getProDefinitionProvider(providerDescriptor);
        assertEquals(definitionProvider, actualDefinitionProvider);

        actualDefinitionProvider = definitionProviderFactory.getProDefinitionProvider(new ProviderDescriptor("UNKNOWN", XMLLoaderType.QUICKFIX_LOADER));
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);
    }

    @Test
    public void testInvalidProvidersDescription() {
        final UserID userID = new UserID(0);
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);

        // Null userID
        FixDefinitionProvider actualDefinitionProvider = definitionProviderFactory.getDefinitionProvider(null, providerDescriptor);
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);

        // Null provider descriptor
        actualDefinitionProvider = definitionProviderFactory.getDefinitionProvider(userID, null);
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);

        // Empty provider name
        actualDefinitionProvider = definitionProviderFactory.getDefinitionProvider(userID, new ProviderDescriptor("", XMLLoaderType.QUICKFIX_LOADER));
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);
    }

    @Test
    public void testDefaultProviderDescription() {
        final UserID userID = new UserID(1);

        FixDefinitionProvider definitionProvider = definitionProviderFactory.getDefinitionProvider(userID, DefaultFixDefinitionProvider.DESCRIPTOR);
        assertTrue(definitionProvider instanceof DefaultFixDefinitionProvider);
    }

    @Test
    public void testDefinitionProviders() {
        final UserID userID = new UserID(3);
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        final FixDefinitionProvider definitionProvider = mock(FixDefinitionProvider.class);

        definitionProviderManager.addDefinitionProvider(userID, providerDescriptor, definitionProvider);

        // Unknown UserID
        FixDefinitionProvider actualDefinitionProvider = definitionProviderFactory.getDefinitionProvider(new UserID(2), providerDescriptor);
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);

        // Unknown ProviderDescriptor
        actualDefinitionProvider = definitionProviderFactory.getDefinitionProvider(userID, new ProviderDescriptor("UNKNOWN", XMLLoaderType.QUICKFIX_LOADER));
        assertTrue(actualDefinitionProvider instanceof DefaultFixDefinitionProvider);

        // Valid situation
        actualDefinitionProvider = definitionProviderFactory.getDefinitionProvider(userID, providerDescriptor);
        assertEquals(definitionProvider, actualDefinitionProvider);
    }

}
