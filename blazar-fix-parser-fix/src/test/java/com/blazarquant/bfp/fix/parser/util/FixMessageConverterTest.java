package com.blazarquant.bfp.fix.parser.util;

import com.blazarquant.bfp.fix.data.FixMessage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageConverterTest {

    private final FixMessageFactory messageFactory = new FixMessageFactory();
    private FixMessageConverter messageConverter;

    @Before
    public void setUp() {
        messageConverter = new FixMessageConverter();
    }

    @Test
    public void testLegalFixMessages() {
        String delimiter = "#";
        List<String> textMessages = new ArrayList<>();
        textMessages.add(FixTestConstants.LEGAL_FIX_LONG_1);
        textMessages.add(FixTestConstants.LEGAL_FIX_LONG_2);
        textMessages.add(FixTestConstants.LEGAL_FIX_LONG_3);

        List<FixMessage> expectedMessages = messageFactory.prepareFixMessagesForLegalFixLong();
        List<FixMessage> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testEmptyFieldValue() {
        String delimiter = "#";
        List<String> textMessages = new ArrayList<>();
        textMessages.add(FixTestConstants.LEGAL_FIX_6);

        List<FixMessage> expectedMessages = new ArrayList<>();
        expectedMessages.add(messageFactory.legalFixMessage6());
        List<FixMessage> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testEmptyVersionValue() {
        String delimiter = "#";
        List<String> textMessages = new ArrayList<>();
        textMessages.add(FixTestConstants.LEGAL_FIX_7);

        List<FixMessage> expectedMessages = new ArrayList<>();
        expectedMessages.add(messageFactory.legalFixMessage7());
        List<FixMessage> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testEdgeCases() {
        String delimiter = "#";
        String[] messages = new String[] {FixTestConstants.EDGE_CASE_FIX_2, FixTestConstants.EDGE_CASE_FIX_3, FixTestConstants.EDGE_CASE_FIX_4};

        for (String message : messages) {
            List<String> textMessages = new ArrayList<>();
            textMessages.add(message);

            List<FixMessage> expectedMessages = new ArrayList<>();
            expectedMessages.add(messageFactory.legalFixMessage7());
            List<FixMessage> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter);
            assertEquals(expectedMessages, actualMessages);
        }
    }

    @Test
    public void testConvertToString() {
        // Default Entry delimiter
        String convertedMessage = messageConverter.convertToString(messageFactory.legalFixMessage4());
        assertEquals(FixTestConstants.LEGAL_FIX_4, convertedMessage);

        // Custom Entry delimiter
        convertedMessage = messageConverter.convertToString(messageFactory.legalFixMessageLong1(), '#');
        assertEquals(FixTestConstants.LEGAL_FIX_LONG_1, convertedMessage);
    }

}
