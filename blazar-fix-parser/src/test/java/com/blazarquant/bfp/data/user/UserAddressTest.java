package com.blazarquant.bfp.data.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class UserAddressTest {

    private final String ADDRESS = "address";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String POSTALCODE = "50-327";
    private final String COUNTRYCODE = "PL";

    @Test
    public void testNullParameters() {
        try {
            UserAddress userAddress = new UserAddress(null, CITY, STATE, POSTALCODE, COUNTRYCODE);
            fail("Test failed. Address cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserAddress userAddress = new UserAddress(ADDRESS, null, STATE, POSTALCODE, COUNTRYCODE);
            fail("Test failed. City cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserAddress userAddress = new UserAddress(ADDRESS, CITY, null, POSTALCODE, COUNTRYCODE);
            fail("Test failed. State cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserAddress userAddress = new UserAddress(ADDRESS, CITY, STATE, null, COUNTRYCODE);
            fail("Test failed. Postalcode cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserAddress userAddress = new UserAddress(ADDRESS, CITY, STATE, POSTALCODE, null);
            fail("Test failed. Countrycode cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
    }

    @Test
    public void testObjectBehaviour() {
        UserAddress userAddress = new UserAddress(ADDRESS, CITY, STATE, POSTALCODE, COUNTRYCODE);
        assertEquals(ADDRESS, userAddress.getAddress());
        assertEquals(CITY, userAddress.getCity());
        assertEquals(STATE, userAddress.getState());
        assertEquals(POSTALCODE, userAddress.getPostalCode());
        assertEquals(COUNTRYCODE, userAddress.getCountryCode());
    }

}
