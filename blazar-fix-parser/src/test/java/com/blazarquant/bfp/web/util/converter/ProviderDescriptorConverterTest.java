package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.core.parser.FixDefinitionProvidersFileUtility;
import com.blazarquant.bfp.fix.parser.definition.data.ProviderDescriptor;
import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class ProviderDescriptorConverterTest {

    private ProviderDescriptorConverter converter;

    @Before
    public void setUp() {
        converter = new ProviderDescriptorConverter();
    }

    @Test
    public void testValidGetAsObject() {
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("Test", XMLLoaderType.QUICKFIX_LOADER);
        final String fileName = FixDefinitionProvidersFileUtility.createProviderFileName(providerDescriptor);

        ProviderDescriptor actualProviderDescriptor = (ProviderDescriptor) converter.getAsObject(null, null, fileName);
        assertEquals(providerDescriptor, actualProviderDescriptor);
    }

    @Test
    public void testInvalidGetAsObject() {
        try {
            converter.getAsObject(null, null, "test");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal file name. Missing XML Loader Type or provider name.", e.getMessage());
        }
    }

    @Test
    public void testValidGetAsString() {
        final ProviderDescriptor providerDescriptor = new ProviderDescriptor("Test", XMLLoaderType.QUICKFIX_LOADER);
        final String fileName = FixDefinitionProvidersFileUtility.createProviderFileName(providerDescriptor);

        String actualFileName = converter.getAsString(null, null, providerDescriptor);
        assertEquals(fileName, actualFileName);
    }

    @Test
    public void testInvalidGetAsString() {
        try {
            converter.getAsString(null, null, new Object());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Failed to convert ProviderDescriptor. Unexpected object type.", e.getMessage());
        }
    }

}
