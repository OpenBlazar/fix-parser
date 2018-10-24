package pl.zankowski.fixparser.messages.api.dictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.ITransferObject;
import pl.zankowski.fixparser.user.api.UserIdTO;

import java.util.Objects;

@JsonPropertyOrder({"userId", "providerName", "loaderType"})
public class DictionaryDescriptorTO implements ITransferObject {

    private static final long serialVersionUID = -2764206970798569139L;

    private final UserIdTO userId;
    private final String providerName;
    private final DictionaryLoaderType loaderType;

    @JsonCreator
    public DictionaryDescriptorTO(
            @JsonProperty("userId") final UserIdTO userId,
            @JsonProperty("providerName") final String providerName,
            @JsonProperty("loaderType") final DictionaryLoaderType loaderType) {
        this.userId = userId;
        this.providerName = providerName;
        this.loaderType = loaderType;
    }

    public UserIdTO getUserId() {
        return userId;
    }

    public String getProviderName() {
        return providerName;
    }

    public DictionaryLoaderType getLoaderType() {
        return loaderType;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DictionaryDescriptorTO that = (DictionaryDescriptorTO) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(providerName, that.providerName) &&
                loaderType == that.loaderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, providerName, loaderType);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("providerName", providerName)
                .add("loaderType", loaderType)
                .toString();
    }
}
