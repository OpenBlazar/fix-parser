package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.Objects;

@JsonPropertyOrder({"fixFieldTag", "fixField", "fixValue"})
public class FixPairTO implements ITransferObject {

    private static final long serialVersionUID = 4404771005740600066L;

    public static final FixPairTO UNKNOWN = new FixPairTOBuilder()
            .withFixFieldTag(0)
            .withFixField(new FixFieldTOBuilder().withTag(0).withName("Unknown").build())
            .withFixValue(new FixValueTOBuilder().withValue("").withDescription("Unknown").build())
            .build();

    private final Integer fixFieldTag;
    private final FixFieldTO fixField;
    private final FixValueTO fixValue;

    @JsonCreator
    public FixPairTO(
            @JsonProperty("fixFieldTag") final Integer fixFieldTag,
            @JsonProperty("fixField") final FixFieldTO fixField,
            @JsonProperty("fixValue") final FixValueTO fixValue) {
        this.fixFieldTag = fixFieldTag;
        this.fixField = fixField;
        this.fixValue = fixValue;
    }

    public Integer getFixFieldTag() {
        return fixFieldTag;
    }

    public FixFieldTO getFixField() {
        return fixField;
    }

    public FixValueTO getFixValue() {
        return fixValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixPairTO fixPairTO = (FixPairTO) o;
        return Objects.equals(fixFieldTag, fixPairTO.fixFieldTag) &&
                Objects.equals(fixField, fixPairTO.fixField) &&
                Objects.equals(fixValue, fixPairTO.fixValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fixFieldTag, fixField, fixValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("fixFieldTag", fixFieldTag)
                .add("fixField", fixField)
                .add("fixValue", fixValue)
                .toString();
    }
}
