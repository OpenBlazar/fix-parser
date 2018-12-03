package pl.zankowski.fixparser.messages.fix;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.api.FixVersion;
import pl.zankowski.fixparser.messages.dictionary.DefaultFixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.loader.QuickFixDictionaryLoader;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptorBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionaryBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("FIX50SP2.xml");
        final QuickFixDictionaryLoader fixXMLLoader = new QuickFixDictionaryLoader();

        final Map<Integer, FixFieldDefinition> values = fixXMLLoader.parseDocument(inputStream);

        final FixDictionary dictionary = new FixDictionaryBuilder()
                .withDictionaryDescriptorEntity(new DictionaryDescriptorBuilder()
                        .build())
                .withDictionaryMap(values)
                .build();

        definitionProvider = new DefaultFixDefinitionProvider(dictionary);
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
        List<FixMessageTO> expectedMessages = messageFactory.prepareFixMessagesForLegalFixLong();
        List<FixMessageTO> actualMessages = parser.parseInput(FixTestConstants.LEGAL_FIX_MANY, definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testEmptyInput() {
        List<FixMessageTO> expectedMessages = new ArrayList<>();
        List<FixMessageTO> actualMessages = parser.parseInput("", definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void testMessageParser() {
        FixPairTO msgType = new FixPairTO(35, new FixFieldTO(35, "MsgType"), new FixValueTO("0", "HEARTBEAT"));
        List<FixPairTO> fixPairs = Arrays.asList(
                new FixPairTO(8, new FixFieldTO(8, "BeginString"), new FixValueTO("FIX.4.3", "")),
                new FixPairTO(9, new FixFieldTO(9, "BodyLength"), new FixValueTO("69", "")),
                msgType,
                new FixPairTO(49, new FixFieldTO(49, "SenderCompID"), new FixValueTO("BrokerQuote", "")),
                new FixPairTO(56, new FixFieldTO(56, "TargetCompID"), new FixValueTO("Client123", "")),
                new FixPairTO(34, new FixFieldTO(34, "MsgSeqNum"), new FixValueTO("670", "")),
                new FixPairTO(57, new FixFieldTO(57, "TargetSubID"), new FixValueTO("FX", "")),
                new FixPairTO(10, new FixFieldTO(10, "CheckSum"), new FixValueTO("074", ""))
        );
        List<FixMessageTO> expectedMessage = Arrays.asList(new FixMessageTO(0L, FixVersion.FIX_43, msgType, fixPairs));
        List<FixMessageTO> actualMessage = parser.parseInput("8=FIX.4.3|9=69|35=0|49=BrokerQuote|56=Client123|34=670|57=FX|10=074|", definitionProvider);
        assertEquals(expectedMessage, actualMessage);
    }

}
