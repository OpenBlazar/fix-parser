package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.ITransferObject;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;

import java.util.Objects;

@JsonPropertyOrder({"dictionaryDescriptor", "input"})
public class FixParserRequestTO implements ITransferObject {

    private static final long serialVersionUID = -1275013073440607748L;

    private final DictionaryDescriptorTO dictionaryDescriptor;
    private final String input;

    @JsonCreator
    public FixParserRequestTO(
            @JsonProperty("dictionaryDescriptor") final DictionaryDescriptorTO dictionaryDescriptor,
            @JsonProperty("input") final String input) {
        this.dictionaryDescriptor = dictionaryDescriptor;
        this.input = input;
    }

    public DictionaryDescriptorTO getDictionaryDescriptor() {
        return dictionaryDescriptor;
    }

    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixParserRequestTO that = (FixParserRequestTO) o;
        return Objects.equals(dictionaryDescriptor, that.dictionaryDescriptor) &&
                Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionaryDescriptor, input);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dictionaryDescriptor", dictionaryDescriptor)
                .add("input", input)
                .toString();
    }
}
