package pl.zankowski.fixparser.messages.api.dictionary;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.Arrays;
import java.util.Objects;

@JsonPropertyOrder({"dictionaryDescriptor", "content"})
public class DictionaryTO implements ITransferObject {

    private static final long serialVersionUID = 8285187365049169634L;

    private final DictionaryDescriptorTO dictionaryDescriptor;
    private final byte[] content;

    @JsonCreator
    public DictionaryTO(
            @JsonProperty("dictionaryDescriptor") final DictionaryDescriptorTO dictionaryDescriptor,
            @JsonProperty("content") final byte[] content) {
        this.dictionaryDescriptor = dictionaryDescriptor;
        this.content = content;
    }

    public DictionaryDescriptorTO getDictionaryDescriptor() {
        return dictionaryDescriptor;
    }

    public byte[] getContent() {
        return content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DictionaryTO that = (DictionaryTO) o;
        return Objects.equals(dictionaryDescriptor, that.dictionaryDescriptor) &&
                Arrays.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionaryDescriptor, content);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dictionaryDescriptor", dictionaryDescriptor)
                .add("content", content)
                .toString();
    }
}
