package com.blazarquant.bfp.fix.parser;

import com.blazarquant.bfp.fix.data.*;
import com.blazarquant.bfp.fix.parser.definition.CustomFixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.FixDefinitionProvider;
import com.blazarquant.bfp.fix.parser.definition.loader.QuickFixXMLLoader;
import com.blazarquant.bfp.fix.parser.util.FixMessageFactory;
import com.blazarquant.bfp.fix.parser.util.FixTestConstants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
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
    private FixDefinitionProvider definitionProvider;

    @Before
    public void setUp() throws Exception {
        parser = new FixParser();

        QuickFixXMLLoader fixXMLLoader = new QuickFixXMLLoader();
        definitionProvider = new CustomFixDefinitionProvider(
                fixXMLLoader.parseDocument(getClass().getClassLoader().getResourceAsStream("FIX50SP2.xml")));
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
        List<FixMessage> actualMessages = parser.parseInput(FixTestConstants.LEGAL_FIX_MANY, definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testEmptyInput() {
        List<FixMessage> expectedMessages = new ArrayList<>();
        List<FixMessage> actualMessages = parser.parseInput("", definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testMessageParser() {
        FixPair msgType = new FixPair(35, new FixField(35, "MsgType"), new FixValue("0", "HEARTBEAT"));
        List<FixPair> fixPairs = Arrays.asList(
                new FixPair(8, new FixField(8, "BeginString"), new FixValue("FIX.4.3")),
                new FixPair(9, new FixField(9, "BodyLength"), new FixValue("69")),
                msgType,
                new FixPair(49, new FixField(49, "SenderCompID"), new FixValue("BrokerQuote")),
                new FixPair(56, new FixField(56, "TargetCompID"), new FixValue("Client123")),
                new FixPair(34, new FixField(34, "MsgSeqNum"), new FixValue("670")),
                new FixPair(57, new  FixField(57, "TargetSubID"), new FixValue("FX")),
                new FixPair(10, new FixField(10, "CheckSum"), new FixValue("074"))
        );
        List<FixMessage> expectedMessage = Arrays.asList(new FixMessage(0L, FixVersion.FIX_43, msgType, fixPairs));
        List<FixMessage> actualMessage = parser.parseInput("8=FIX.4.3|9=69|35=0|49=BrokerQuote|56=Client123|34=670|57=FX|10=074|", definitionProvider);
        assertEquals(expectedMessage, actualMessage);
    }

}
