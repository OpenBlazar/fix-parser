package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.Objects;

@JsonPropertyOrder({"value", "description"})
public class FixValueTO implements ITransferObject {

    private final String value;
    private final String description;

    @JsonCreator
    public FixValueTO(
            @JsonProperty("value") final String value,
            @JsonProperty("description") final String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixValueTO fixValue = (FixValueTO) o;
        return Objects.equals(value, fixValue.value) &&
                Objects.equals(description, fixValue.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, description);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value)
                .add("description", description)
                .toString();
    }
}
