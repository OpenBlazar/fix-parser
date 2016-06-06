package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.database.util.DatabaseTestBase;
import com.blazarquant.bfp.fix.data.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class MessageDAOTest extends DatabaseTestBase {

    @Test
    public void testSelectNumberOfMsgFromUserID() {
        int countUser9 = messageDAO.countUserMessages(new UserID(9));
        assertEquals(3, countUser9);

        int countUser8 = messageDAO.countUserMessages(new UserID(8));
        assertEquals(1, countUser8);
    }

    @Test
    public void testSelectMessagesByUserID() {
        List<String> messages = messageDAO.findMessageByUserID(new UserID(9), 0, 2);
        assertEquals(2, messages.size());

        List<String> expectedMessages = Arrays.asList("message1", "message2");
        assertEquals(expectedMessages, messages);
    }

    @Test
    public void testInsertMessage() {
        List<String> messages = messageDAO.findMessageByUserID(new UserID(8), 0, 100);
        assertEquals(1, messages.size());

        FixPair fixPair = new FixPair(8, new FixField(8, "BeginString"), new FixValue("FIX.4.2"));
        messageDAO.saveMessage(new UserID(8), new FixMessage(0L, FixVersion.FIX_42, fixPair, Arrays.asList(fixPair)));

        List<String> expectedMessages = Arrays.asList("message1", "OD1GSVguNC4yAQ==");
        messages = messageDAO.findMessageByUserID(new UserID(8), 0, 100);
        assertEquals(expectedMessages, messages);
    }

    @Test
    public void testClearHistory() {
        List<String> messages = messageDAO.findMessageByUserID(new UserID(9), 0, 100);
        assertEquals(3, messages.size());

        messageDAO.clearHistory(new UserID(9));

        messages = messageDAO.findMessageByUserID(new UserID(9), 0, 100);
        assertEquals(0, messages.size());
    }

}
