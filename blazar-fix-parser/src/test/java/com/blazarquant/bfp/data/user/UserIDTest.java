package com.blazarquant.bfp.data.user;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class UserIDTest {

    @Test
    public void testObjectBehaviour() {
        int id = 9;
        UserID userID = new UserID(id);
        assertEquals(id, userID.getId());
    }

}
