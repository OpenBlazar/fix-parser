package pl.zankowski.fixparser.messages.entity.dictionary;

import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryLoaderType;
import pl.zankowski.fixparser.user.api.UserId;

public final class DictionaryDescriptorBuilder {
    private UserId userId;
    private String providerName;
    private DictionaryLoaderType loaderType;

    public DictionaryDescriptorBuilder() {
    }

    public DictionaryDescriptorBuilder(final DictionaryDescriptorTO providerDescriptor) {
        this.userId = providerDescriptor.getUserId();
        this.providerName = providerDescriptor.getProviderName();
        this.loaderType = providerDescriptor.getLoaderType();
    }

    public DictionaryDescriptorBuilder withUserId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public DictionaryDescriptorBuilder withProviderName(String providerName) {
        this.providerName = providerName;
        return this;
    }

    public DictionaryDescriptorBuilder withLoaderType(DictionaryLoaderType loaderType) {
        this.loaderType = loaderType;
        return this;
    }

    public DictionaryDescriptor build() {
        return new DictionaryDescriptor(userId, providerName, loaderType);
    }
}
