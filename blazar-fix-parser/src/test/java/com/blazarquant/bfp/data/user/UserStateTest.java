package com.blazarquant.bfp.data.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class UserStateTest {

    @Test
    public void testObjectBehaviour() {
        assertEquals(-1, UserState.UNKNOWN.getState());
        assertEquals(1, UserState.ACTIVE.getState());
        assertEquals(0, UserState.INACTIVE.getState());
    }

    @Test
    public void testGetUserStateFromCode() {
        assertEquals(UserState.UNKNOWN, UserState.getUserStateFromCode(10));
        assertEquals(UserState.ACTIVE, UserState.getUserStateFromCode(1));
        assertEquals(UserState.INACTIVE, UserState.getUserStateFromCode(0));

    }

}
