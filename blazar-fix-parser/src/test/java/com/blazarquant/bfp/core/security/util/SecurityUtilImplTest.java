package com.blazarquant.bfp.core.security.util;

import com.blazarquant.bfp.core.security.exception.DecodingException;
import org.apache.shiro.codec.Base64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Wojciech Zankowski
 */
public class SecurityUtilImplTest {

    private final String PASSWORD = "password";
    private final String EMPTY_PASSWORD = "";

    private SecurityUtil securityUtil;

    @Before
    public void setUp() {
        securityUtil = new SecurityUtilImpl();
    }

    @Test
    public void testBCryptUtils() {
        String hashedPassword = securityUtil.hashPassword(PASSWORD.toCharArray());

        assertTrue(securityUtil.checkPassword(PASSWORD.toCharArray(), hashedPassword));
        assertFalse(securityUtil.checkPassword("FAKE".toCharArray(), hashedPassword));

        hashedPassword = securityUtil.hashPassword(EMPTY_PASSWORD.toCharArray());

        assertTrue(securityUtil.checkPassword(EMPTY_PASSWORD.toCharArray(), hashedPassword));
        assertFalse(securityUtil.checkPassword(PASSWORD.toCharArray(), hashedPassword));
    }

    @Test
    public void testBase64Utils() throws DecodingException {
        String confirmationKey = securityUtil.generateConfirmationKey(0, "user", "mail");
        assertEquals(0, securityUtil.decodeConfirmationKey(confirmationKey));

        try {
            confirmationKey = securityUtil.generateConfirmationKey(1, "", "mail");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Failed to generate confirmation key. Username or email is empty.", e.getMessage());
        }

        try {
            confirmationKey = securityUtil.generateConfirmationKey(2, "user", "");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Failed to generate confirmation key. Username or email is empty.", e.getMessage());
        }

        confirmationKey = Base64.encodeToString("fake".getBytes());
        try {
            securityUtil.decodeConfirmationKey(confirmationKey);
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Illegal confirmation key. Please contact with admin.", e.getMessage());
        }

        confirmationKey = Base64.encodeToString("f;a;ke".getBytes());
        try {
            securityUtil.decodeConfirmationKey(confirmationKey);
            fail();
        } catch (NumberFormatException e) {
        }
    }

    @Test
    public void testShareKey() {
        String firstKey = securityUtil.generateShareKey();
        String secondKey = securityUtil.generateShareKey();

        assertFalse(firstKey.equals(secondKey));
    }

}
