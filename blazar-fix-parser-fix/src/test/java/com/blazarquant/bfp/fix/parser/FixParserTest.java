package com.blazarquant.bfp.fix.parser;

import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.util.FixTestConstants;
import com.blazarquant.bfp.fix.parser.util.FixMessageFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class FixParserTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private final FixMessageFactory messageFactory = new FixMessageFactory();
    private FixParser parser;

    @Before
    public void setUp() {
        parser = new FixParser();
    }

    @Test
    public void testLegalFixDelimiterResolve() {
        assertEquals("|", parser.resolveMessageDelimiter(FixTestConstants.LEGAL_FIX_1));
        assertEquals("#", parser.resolveMessageDelimiter(FixTestConstants.LEGAL_FIX_2));
        assertEquals("#", parser.resolveMessageDelimiter(FixTestConstants.LEGAL_FIX_3));
        assertEquals("\u0001", parser.resolveMessageDelimiter(FixTestConstants.LEGAL_FIX_4));
    }

    @Test
    public void testIllegalFixDelimiterResolve_1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        parser.resolveMessageDelimiter(FixTestConstants.ILLEGAL_FIX_1);
    }

    @Test
    public void testIllegalFixDelimiterResolve_2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        parser.resolveMessageDelimiter(FixTestConstants.ILLEGAL_FIX_2);
    }

    @Test
    public void testIllegalFixDelimiterResolve_3() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find body length value."));
        parser.resolveMessageDelimiter(FixTestConstants.ILLEGAL_FIX_3);
    }

    @Test
    public void testIllegalFixDelimiterResolve_4() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(containsString("Failed to find message type value."));
        parser.resolveMessageDelimiter(FixTestConstants.ILLEGAL_FIX_5);
    }

    @Test
    public void testWholeLegalFixInput() {
        List<FixMessage> expectedMessages = messageFactory.prepareFixMessagesForLegalFixLong();
        List<FixMessage> actualMessages = parser.parseInput(FixTestConstants.LEGAL_FIX_MANY);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testEmptyInput() {
        List<FixMessage> expectedMessages = new ArrayList<>();
        List<FixMessage> actualMessages = parser.parseInput("");
        assertEquals(expectedMessages, actualMessages);
    }

}
