package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.database.util.DatabaseTestBase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class ShareDAOTest extends DatabaseTestBase {

    @Test
    public void testSelectMessage() {
        String message = shareDAO.findMessageByKey("44d7cbfe-64a5-4f4c-9c11-b6bb67d7495b");
        assertEquals("OD1GSVguNC4yIzk9MTIzIzM1PUQjNDk9QmxhemFyUXVhbnQjNTY9QnJva2VyIzUyPTIwMTYwMzI1LTIzOjAzOjM0Ljc4NCMxMT0xMDA0IzM4PTEwMCM0MD0yIzQ0PTEwMCM1ND0xIzU1PUtPIzU5PTAjMTA9MDY0Iw==", message);
    }

    @Test
    public void testInsertMessage() {
        String key = "key";
        String value = "message";
        shareDAO.saveSharedMessage(key, value);

        String message = shareDAO.findMessageByKey(key);
        assertEquals(value, message);
    }

}
