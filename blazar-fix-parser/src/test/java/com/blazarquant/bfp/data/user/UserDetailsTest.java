package com.blazarquant.bfp.data.user;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class UserDetailsTest {

    private final UserID USER_ID = new UserID(9);
    private final String USER_NAME = "Test";
    private final String USER_MAIL = "test@test.com";
    private final String PASSWORD = "test";
    private final UserState USER_STATE = UserState.ACTIVE;
    private final Instant REGISTRATION_DATE = Instant.parse("2016-06-06T16:16:16Z");
    private final Instant LAST_LOGIN = Instant.parse("2016-06-06T18:16:16Z");

    @Test
    public void testNullParameter() {
        try {
            UserDetails userDetails = new UserDetails(null, USER_NAME, USER_MAIL, PASSWORD, USER_STATE, REGISTRATION_DATE, LAST_LOGIN);
            fail("Test failed. UserID cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserDetails userDetails = new UserDetails(USER_ID, null, USER_MAIL, PASSWORD, USER_STATE, REGISTRATION_DATE, LAST_LOGIN);
            fail("Test failed. Username cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserDetails userDetails = new UserDetails(USER_ID, USER_NAME, null, PASSWORD, USER_STATE, REGISTRATION_DATE, LAST_LOGIN);
            fail("Test failed. User mail cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserDetails userDetails = new UserDetails(USER_ID, USER_NAME, USER_MAIL, null, USER_STATE, REGISTRATION_DATE, LAST_LOGIN);
            fail("Test failed. Password cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserDetails userDetails = new UserDetails(USER_ID, USER_NAME, USER_MAIL, PASSWORD, null, REGISTRATION_DATE, LAST_LOGIN);
            fail("Test failed. User State cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserDetails userDetails = new UserDetails(USER_ID, USER_NAME, USER_MAIL, PASSWORD, USER_STATE, null, LAST_LOGIN);
            fail("Test failed. Registration date cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
        try {
            UserDetails userDetails = new UserDetails(USER_ID, USER_NAME, USER_MAIL, PASSWORD, USER_STATE, REGISTRATION_DATE, null);
            fail("Test failed. Last login cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
    }

    @Test
    public void testObjectBehaviour() {
        UserDetails userDetails = new UserDetails(USER_ID, USER_NAME, USER_MAIL, PASSWORD, USER_STATE, REGISTRATION_DATE, LAST_LOGIN);
        assertEquals(USER_ID, userDetails.getUserID());
        assertEquals(USER_NAME, userDetails.getUserName());
        assertEquals(USER_MAIL, userDetails.getUserMail());
        assertEquals(PASSWORD, userDetails.getPassword());
        assertEquals(USER_STATE, userDetails.getUserState());
        assertEquals(REGISTRATION_DATE, userDetails.getRegistrationDate());
        assertEquals(LAST_LOGIN, userDetails.getLastLogin());
    }

}
