package com.blazarquant.bfp.core.parser;

import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import com.blazarquant.bfp.fix.parser.definition.loader.QuickFixXMLLoader;
import com.blazarquant.bfp.fix.parser.definition.loader.XMLLoader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Wojciech Zankowski
 */
public class FixDefinitionProvidersLoaderFactoryTest {

    private FixDefinitionProvidersLoaderFactory loaderFactory;

    @Before
    public void setUp() {
        loaderFactory = new FixDefinitionProvidersLoaderFactory();
    }

    @Test
    public void testFactory() {
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("NAME", XMLLoaderType.QUICKFIX_LOADER);
        XMLLoader xmlLoader = loaderFactory.getLoader(providerDescriptor);
        assertTrue(xmlLoader instanceof QuickFixXMLLoader);
    }

}
