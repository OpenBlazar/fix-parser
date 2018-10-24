package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.Objects;

@JsonPropertyOrder({"tag", "name"})
public class FixFieldTO implements ITransferObject {

    private static final long serialVersionUID = -73301889446387415L;

    private final int tag;
    private final String name;

    @JsonCreator
    public FixFieldTO(
            @JsonProperty("tag") final int tag,
            @JsonProperty("name") final String name) {
        this.tag = tag;
        this.name = name;
    }

    public int getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixFieldTO that = (FixFieldTO) o;
        return tag == that.tag &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("tag", tag)
                .add("name", name)
                .toString();
    }
}
