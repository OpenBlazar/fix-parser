package pl.zankowski.fixparser.messages.fix;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.dictionary.DefaultFixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.loader.QuickFixDictionaryLoader;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptorBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionaryBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class FixMessageConverterTest {

    private final FixMessageFactory messageFactory = new FixMessageFactory();
    private FixMessageConverter messageConverter;
    private FixDefinitionProvider definitionProvider;

    @Before
    public void setUp() throws Exception {
        messageConverter = new FixMessageConverter();

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
    public void testLegalFixMessages() {
        String delimiter = "#";
        List<String> textMessages = new ArrayList<>();
        textMessages.add(FixTestConstants.LEGAL_FIX_LONG_1);
        textMessages.add(FixTestConstants.LEGAL_FIX_LONG_2);
        textMessages.add(FixTestConstants.LEGAL_FIX_LONG_3);

        List<FixMessageTO> expectedMessages = messageFactory.prepareFixMessagesForLegalFixLong();
        List<FixMessageTO> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Ignore
    @Test
    public void testEmptyFieldValue() {
        String delimiter = "#";
        List<String> textMessages = new ArrayList<>();
        textMessages.add(FixTestConstants.LEGAL_FIX_6);

        List<FixMessageTO> expectedMessages = new ArrayList<>();
        expectedMessages.add(messageFactory.legalFixMessage6());
        List<FixMessageTO> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Ignore
    @Test
    public void testEmptyVersionValue() {
        String delimiter = "#";
        List<String> textMessages = new ArrayList<>();
        textMessages.add(FixTestConstants.LEGAL_FIX_7);

        List<FixMessageTO> expectedMessages = new ArrayList<>();
        expectedMessages.add(messageFactory.legalFixMessage7());
        List<FixMessageTO> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
        assertEquals(expectedMessages, actualMessages);
    }

    @Ignore
    @Test
    public void testEdgeCases() {
        String delimiter = "#";
        String[] messages = new String[]{FixTestConstants.EDGE_CASE_FIX_2, FixTestConstants.EDGE_CASE_FIX_3, FixTestConstants.EDGE_CASE_FIX_4};

        for (String message : messages) {
            List<String> textMessages = new ArrayList<>();
            textMessages.add(message);

            List<FixMessageTO> expectedMessages = new ArrayList<>();
            expectedMessages.add(messageFactory.legalFixMessage7());
            List<FixMessageTO> actualMessages = messageConverter.convertToFixMessages(textMessages, delimiter, definitionProvider);
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
