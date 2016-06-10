package com.blazarquant.bfp.services.parser;

import com.blazarquant.bfp.common.TestObjectFactory;
import com.blazarquant.bfp.data.user.UserDetails;
import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.database.dao.MessageDAO;
import com.blazarquant.bfp.fix.data.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Wojciech Zankowski
 */
public class ParserServiceImplTest {

    private ParserService parserService;
    private MessageDAO messageDAO;

    @Before
    public void setUp() throws Exception {
        messageDAO = mock(MessageDAO.class);
        parserService = new ParserServiceImpl(messageDAO);
    }

    @Test
    public void testCountUserMessages() {
        final UserID userID = new UserID(0);
        final UserDetails userDetails = TestObjectFactory.createUserDetails(userID, "test");
        final int messages = 14;
        when(messageDAO.countUserMessages(userID)).thenReturn(messages);

        int actualMessages = parserService.countUserMessages(userDetails);
        assertEquals(messages, actualMessages);
    }

    @Test
    public void testParseInput() {
        final String input = "8=FIX.4.2#9=14#35=0#10=213#";
        List<FixMessage> messageList = parserService.parseInput(input);
        assertEquals(1, messageList.size());

        FixPair msgType = new FixPair(35, new FixField(35, "MsgType"), new FixValue("0", "HEARTBEAT"));
        List<FixPair> fixPairList = Arrays.asList(
                new FixPair(8, new FixField(8, "BeginString"), new FixValue("FIX.4.2")),
                new FixPair(9, new FixField(9, "BodyLength"), new FixValue("14")),
                msgType,
                new FixPair(10, new FixField(10, "CheckSum"), new FixValue("213"))
        );
        FixMessage fixMessage = new FixMessage(0L, FixVersion.FIX_42, msgType, fixPairList);

        assertEquals(fixMessage, messageList.get(0));
    }

}
