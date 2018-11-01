package pl.zankowski.fixparser.messages.entity.dictionary;

import java.util.Map;

public final class FixDictionaryBuilder {
    private DictionaryDescriptor dictionaryDescriptor;
    private Map<Integer, FixFieldDefinition> dictionaryMap;

    public FixDictionaryBuilder withDictionaryDescriptorEntity(final DictionaryDescriptor dictionaryDescriptor) {
        this.dictionaryDescriptor = dictionaryDescriptor;
        return this;
    }

    public FixDictionaryBuilder withDictionaryMap(final Map<Integer, FixFieldDefinition> dictionaryMap) {
        this.dictionaryMap = dictionaryMap;
        return this;
    }

    public FixDictionary build() {
        return new FixDictionary(dictionaryDescriptor, dictionaryMap);
    }
}
