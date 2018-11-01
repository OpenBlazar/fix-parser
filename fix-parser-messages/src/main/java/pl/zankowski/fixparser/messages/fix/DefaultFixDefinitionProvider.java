package pl.zankowski.fixparser.messages.fix;

import com.google.common.collect.ImmutableMap;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixFieldTOBuilder;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.api.FixValueTOBuilder;
import pl.zankowski.fixparser.messages.entity.dictionary.FixDictionary;
import pl.zankowski.fixparser.messages.entity.dictionary.FixFieldDefinition;

import java.util.Map;

class DefaultFixDefinitionProvider implements FixDefinitionProvider {

    private final Map<Integer, FixFieldDefinition> dictionaryMap;

    DefaultFixDefinitionProvider(final FixDictionary fixDictionary) {
        this.dictionaryMap = ImmutableMap.copyOf(fixDictionary.getDictionaryMap());
    }

    @Override
    public FixFieldTO getFixField(final int tag) {
        final FixFieldDefinition fieldDefinition = dictionaryMap.get(tag);
        if (fieldDefinition == null) {
            return new FixFieldTOBuilder()
                    .withTag(tag)
                    .withName(UNKNOWN)
                    .build();
        }
        return new FixFieldTOBuilder()
                .withName(fieldDefinition.getField().getName())
                .withTag(fieldDefinition.getField().getTag())
                .build();
    }

    @Override
    public FixValueTO getFixValue(int tag, String valueEnum) {
        final FixFieldDefinition fieldDefinition = dictionaryMap.get(tag);
        if (fieldDefinition == null) {
            return new FixValueTOBuilder()
                    .withValue(valueEnum)
                    .withNoDescription()
                    .build();
        }

        final String valueDescription = fieldDefinition.getValues().get(valueEnum);
        if (valueDescription == null) {
            return new FixValueTOBuilder()
                    .withValue(valueEnum)
                    .withNoDescription()
                    .build();
        }

        return new FixValueTOBuilder()
                .withValue(valueEnum)
                .withDescription(valueDescription)
                .build();
    }
}
