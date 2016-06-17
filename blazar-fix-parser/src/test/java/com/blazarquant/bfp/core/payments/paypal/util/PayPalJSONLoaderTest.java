package com.blazarquant.bfp.core.payments.paypal.util;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class PayPalJSONLoaderTest {

    private PayPalJSONLoader loader;

    @Before
    public void setUp() {
        loader = new PayPalJSONLoader();
    }

    @Test
    public void testJSON() throws IOException {
        String jsonFile = getClass().getClassLoader().getResource("com/blazarquant/bfp/core/payments/paypal/util/testJSON.json").getFile();
        JSONTest testObject = loader.load(jsonFile, JSONTest.class);

        assertEquals("test2", testObject.getTest());
    }

    @Test
    public void testJSONArray() throws IOException {
        String jsonFile = getClass().getClassLoader().getResource("com/blazarquant/bfp/core/payments/paypal/util/testJSONArray.json").getFile();
        JSONTest[] testObjects = loader.load(jsonFile, JSONTest[].class);

        assertEquals(2, testObjects.length);
        assertEquals("test1", testObjects[0].getTest());
        assertEquals("test2", testObjects[1].getTest());
    }

    private class JSONTest {

        private String test;

        public String getTest() {
            return test;
        }

        public void setTest(String test) {
            this.test = test;
        }

    }

}
