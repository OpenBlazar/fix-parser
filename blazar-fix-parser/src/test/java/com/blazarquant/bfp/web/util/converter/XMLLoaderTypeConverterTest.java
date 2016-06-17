package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.fix.parser.definition.data.XMLLoaderType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class XMLLoaderTypeConverterTest {

    private XMLLoaderTypeConverter converter;

    @Before
    public void setUp() {
        converter = new XMLLoaderTypeConverter();
    }

    @Test
    public void testValidGetAsObject() {
        final XMLLoaderType loaderType = XMLLoaderType.QUICKFIX_LOADER;
        XMLLoaderType expectedLoaderType = (XMLLoaderType) converter.getAsObject(null, null, loaderType.name());
        assertEquals(expectedLoaderType, loaderType);
    }

    @Test
    public void testValidGetAsString() {
        final XMLLoaderType loaderType = XMLLoaderType.QUICKFIX_LOADER;
        String actualLoader = converter.getAsString(null, null, loaderType);
        assertEquals(loaderType.name(), actualLoader);
    }

    @Test
    public void testInvalidGetAsString() {
        try {
            converter.getAsString(null, null, new Object());
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Failed to convert XMLLoaderType. Unexpected object type.", e.getMessage());
        }
    }

}
