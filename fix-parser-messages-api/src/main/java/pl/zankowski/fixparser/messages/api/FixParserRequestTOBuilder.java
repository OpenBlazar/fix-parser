package pl.zankowski.fixparser.messages.api;

import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;

public final class FixParserRequestTOBuilder {
    private DictionaryDescriptorTO dictionaryDescriptor;
    private String input;

    public FixParserRequestTOBuilder withDictionaryDescriptor(DictionaryDescriptorTO dictionaryDescriptor) {
        this.dictionaryDescriptor = dictionaryDescriptor;
        return this;
    }

    public FixParserRequestTOBuilder withInput(String input) {
        this.input = input;
        return this;
    }

    public FixParserRequestTO build() {
        return new FixParserRequestTO(dictionaryDescriptor, input);
    }
}
