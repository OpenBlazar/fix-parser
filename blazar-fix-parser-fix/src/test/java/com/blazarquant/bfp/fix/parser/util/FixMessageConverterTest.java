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

}
