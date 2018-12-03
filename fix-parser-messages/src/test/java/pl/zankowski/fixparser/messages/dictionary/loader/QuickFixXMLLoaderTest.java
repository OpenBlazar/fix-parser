package pl.zankowski.fixparser.messages.dictionary.loader;

import org.junit.Before;
import org.junit.Test;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.dictionary.DefaultFixDefinitionProvider;
import pl.zankowski.fixparser.messages.dictionary.FixDefinitionProvider;
import pl.zankowski.fixparser.messages.entity.dictionary.DictionaryDescriptorBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionaryBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;

import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class QuickFixXMLLoaderTest {

    private FixDefinitionProvider definitionProvider;

    @Before
    public void setUp() throws Exception {
        final InputStream inputStream = getClass().getClassLoader().getResourceAsStream("CUSTOM.xml");
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
    public void testXMLLoader() {
        FixFieldTO advSideField = definitionProvider.getFixField(4);
        assertEquals(4, advSideField.getTag());
        assertEquals("AdvSide", advSideField.getName());

        FixValueTO advSideSellValue = definitionProvider.getFixValue(4, "S");
        assertEquals("S", advSideSellValue.getValue());
        assertEquals("SELL", advSideSellValue.getDescription());

        FixValueTO advSideBuyValue = definitionProvider.getFixValue(4, "B");
        assertEquals("B", advSideBuyValue.getValue());
        assertEquals("BUY", advSideBuyValue.getDescription());

        FixFieldTO advTransType = definitionProvider.getFixField(5);
        assertEquals(5, advTransType.getTag());
        assertEquals("AdvTransType", advTransType.getName());

        FixValueTO advTransTypeNew = definitionProvider.getFixValue(5, "N");
        assertEquals("N", advTransTypeNew.getValue());
        assertEquals("NEW", advTransTypeNew.getDescription());

        FixFieldTO advTransType2 = definitionProvider.getFixField(6);
        assertEquals(6, advTransType2.getTag());
        assertEquals("AdvTransType2", advTransType2.getName());

        FixValueTO advTransType2Cancel = definitionProvider.getFixValue(6, "C");
        assertEquals("C", advTransType2Cancel.getValue());
        assertEquals("CANCEL", advTransType2Cancel.getDescription());
    }


}
