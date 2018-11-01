package pl.zankowski.fixparser.messages.api.dictionary;

import pl.zankowski.fixparser.user.api.UserId;

public final class DictionaryDescriptorTOBuilder {
    private UserId userId;
    private String providerName;
    private DictionaryLoaderType loaderType;

    public DictionaryDescriptorTOBuilder withUserId(final UserId userId) {
        this.userId = userId;
        return this;
    }

    public DictionaryDescriptorTOBuilder withProviderName(final String providerName) {
        this.providerName = providerName;
        return this;
    }

    public DictionaryDescriptorTOBuilder withLoaderType(final DictionaryLoaderType loaderType) {
        this.loaderType = loaderType;
        return this;
    }

    public DictionaryDescriptorTO build() {
        return new DictionaryDescriptorTO(userId, providerName, loaderType);
    }
}
