package com.blazarquant.bfp.web.util.converter;

import com.blazarquant.bfp.data.payments.paypal.PayPalCountryCodes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Wojciech Zankowski
 */
public class PayPalCountryCodeConverterTest {

    private PayPalCountryCodeConverter converter;

    @Before
    public void setUp() {
        converter = new PayPalCountryCodeConverter();
    }

    @Test
    public void testValidGetAsObject() {
        final PayPalCountryCodes countryCode = PayPalCountryCodes.POLAND;
        PayPalCountryCodes actualCountryCode = (PayPalCountryCodes) converter.getAsObject(null, null, countryCode.name());
        assertEquals(countryCode, actualCountryCode);
    }

    @Test
    public void testInvalidGetAsObject() {
        try {
            converter.getAsObject(null, null, "unknown");
            fail("IllegalArgumentException expected");
        } catch (Exception e) {
            assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testValidGetAsString() {
        final PayPalCountryCodes countryCode = PayPalCountryCodes.ALBANIA;
        String actualCode = converter.getAsString(null, null, countryCode);
        assertEquals(countryCode.name(), actualCode);
    }

    @Test
    public void testInvalidGetAsString() {
        try {
            converter.getAsString(null, null, new Object());
        } catch (IllegalArgumentException e) {
            assertEquals("Failed to convert PayPalCountryCode. Unexpected object type.", e.getMessage());
        }
    }

}
