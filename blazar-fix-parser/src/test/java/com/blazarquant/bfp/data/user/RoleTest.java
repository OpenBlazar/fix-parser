package com.blazarquant.bfp.data.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class RoleTest {

    @Test
    public void testNullParameter() {
        try {
            Role role = new Role(null);
            fail("Test failed, object constructed with null parameter.");
        } catch (NullPointerException e) {
            // Success
        }
    }

    @Test
    public void testObjectBehavior() {
        String parameter = "Test";

        Role role = new Role(parameter);
        assertEquals(parameter, role.getName());
    }

    @Test
    public void testInstances() {
        assertEquals("Admin", Role.ADMIN_ROLE.getName());
        assertEquals("User", Role.USER_ROLE.getName());
    }

}
