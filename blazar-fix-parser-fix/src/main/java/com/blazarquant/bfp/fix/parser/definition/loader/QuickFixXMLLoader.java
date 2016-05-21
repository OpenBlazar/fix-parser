package com.blazarquant.bfp.fix.parser.definition.loader;

import com.blazarquant.bfp.fix.data.definition.FixDictionary;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class QuickFixXMLLoader implements XMLLoader {

    private static final String FIELDS_TAG = "fields";

    private static final String FIELD_TAG = "field";
    private static final String FIELD_NUMBER_LOCAL = "number";
    private static final String FIELD_NAME_LOCAL = "name";

    private static final String VALUE_TAG = "value";
    private static final String VALUE_ENUM_LOCAL = "enum";
    private static final String VALUE_DESCRIPTION_LOCAL = "description";

    @Override
    public Map<Integer, FixDictionary> parseDocument(InputStream documentFile) throws Exception {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        XMLStreamReader streamReader = inputFactory.createXMLStreamReader(documentFile);

        boolean insideFields = false;
        boolean insideField = false;

        Map<Integer, FixDictionary> dictionaryMap = new HashMap<>();
        FixDictionary.Builder builder = null;

        while (streamReader.hasNext()) {
            int event = streamReader.next();

            if (XMLStreamConstants.START_ELEMENT == event && FIELDS_TAG.equals(streamReader.getLocalName())) {
                insideFields = true;
            } else if (XMLStreamConstants.END_ELEMENT == event && FIELDS_TAG.equals(streamReader.getLocalName())) {
                break;
            }

            if (insideField) {
                if (XMLStreamConstants.START_ELEMENT == event && VALUE_TAG.equals(streamReader.getLocalName())) {
                    String name = streamReader.getAttributeValue(null, VALUE_ENUM_LOCAL);
                    String description = streamReader.getAttributeValue(null, VALUE_DESCRIPTION_LOCAL);
                    builder.value(name, description);
                }
            }

            if (insideFields) {
                if (XMLStreamConstants.START_ELEMENT == event && FIELD_TAG.equals(streamReader.getLocalName())) {
                    int tag = Integer.parseInt(streamReader.getAttributeValue(null, FIELD_NUMBER_LOCAL));
                    String name = streamReader.getAttributeValue(null, FIELD_NAME_LOCAL);
                    builder = new FixDictionary.Builder()
                            .tag(tag)
                            .name(name);
                    insideField = true;
                } else if (XMLStreamConstants.END_ELEMENT == event && FIELD_TAG.equals(streamReader.getLocalName())) {
                    FixDictionary fixDictionary = builder.build();
                    dictionaryMap.put(fixDictionary.getTag(), fixDictionary);
                    insideField = false;
                }
            }
        }

        return dictionaryMap;
    }

}
