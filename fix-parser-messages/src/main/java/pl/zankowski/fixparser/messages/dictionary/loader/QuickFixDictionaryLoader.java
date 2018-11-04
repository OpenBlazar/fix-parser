package pl.zankowski.fixparser.messages.dictionary.loader;

import pl.zankowski.fixparser.core.exception.FixParserBusinessException;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinitionBuilder;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class QuickFixDictionaryLoader implements DictionaryLoader {

    private static final String FIELDS_TAG = "fields";

    private static final String FIELD_TAG = "field";
    private static final String FIELD_NUMBER_LOCAL = "number";
    private static final String FIELD_NAME_LOCAL = "name";

    private static final String VALUE_TAG = "value";
    private static final String VALUE_ENUM_LOCAL = "enum";
    private static final String VALUE_DESCRIPTION_LOCAL = "description";

    @Override
    public Map<Integer, FixFieldDefinition> parseDocument(InputStream documentFile) throws FixParserBusinessException {
        try {
            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            final XMLStreamReader streamReader = inputFactory.createXMLStreamReader(documentFile);

            boolean insideFields = false;
            boolean insideField = false;

            final Map<Integer, FixFieldDefinition> dictionaryMap = new HashMap<>();
            FixFieldDefinitionBuilder builder = new FixFieldDefinitionBuilder();

            while (streamReader.hasNext()) {
                final int event = streamReader.next();

                if (XMLStreamConstants.START_ELEMENT == event && FIELDS_TAG.equals(streamReader.getLocalName())) {
                    insideFields = true;
                } else if (XMLStreamConstants.END_ELEMENT == event && FIELDS_TAG.equals(streamReader.getLocalName())) {
                    break;
                }

                if (insideField) {
                    if (XMLStreamConstants.START_ELEMENT == event && VALUE_TAG.equals(streamReader.getLocalName())) {
                        final String name = streamReader.getAttributeValue(null, VALUE_ENUM_LOCAL);
                        final String description = streamReader.getAttributeValue(null, VALUE_DESCRIPTION_LOCAL);
                        builder.withValue(name, description);
                    }
                }

                if (insideFields) {
                    if (XMLStreamConstants.START_ELEMENT == event && FIELD_TAG.equals(streamReader.getLocalName())) {
                        final int tag = Integer.parseInt(streamReader.getAttributeValue(null, FIELD_NUMBER_LOCAL));
                        final String name = streamReader.getAttributeValue(null, FIELD_NAME_LOCAL);
                        builder = new FixFieldDefinitionBuilder()
                                .withField(new FixFieldBuilder()
                                        .withTag(tag)
                                        .withName(name)
                                        .build());
                        insideField = true;
                    } else if (XMLStreamConstants.END_ELEMENT == event && FIELD_TAG.equals(streamReader.getLocalName())) {
                        final FixFieldDefinition fieldDefinition = builder.build();
                        dictionaryMap.put(fieldDefinition.getField().getTag(), fieldDefinition);
                        insideField = false;
                    }
                }
            }

            return dictionaryMap;
        } catch (Exception e) {
            throw new FixParserBusinessException(e);
        }
    }

    @Override
    public DictionaryLoaderType getType() {
        return DictionaryLoaderType.QUICKFIX_LOADER;
    }
}
