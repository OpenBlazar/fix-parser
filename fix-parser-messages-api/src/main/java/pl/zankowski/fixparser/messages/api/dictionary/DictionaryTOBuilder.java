package pl.zankowski.fixparser.messages.api.dictionary;

public final class DictionaryTOBuilder {
    private DictionaryDescriptorTO dictionaryDescriptor;
    private byte[] content;

    public DictionaryTOBuilder withDictionaryDescriptor(final DictionaryDescriptorTO dictionaryDescriptor) {
        this.dictionaryDescriptor = dictionaryDescriptor;
        return this;
    }

    public DictionaryTOBuilder withContent(final byte[] content) {
        this.content = content;
        return this;
    }

    public DictionaryTO build() {
        return new DictionaryTO(dictionaryDescriptor, content);
    }
}
