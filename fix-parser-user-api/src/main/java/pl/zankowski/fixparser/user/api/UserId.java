package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.zankowski.fixparser.core.ITransferObject;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id"})
public class UserId implements ITransferObject {

    private static final long serialVersionUID = 1926039666988035924L;

    private final long id;

    @JsonCreator
    public UserId(
            @JsonProperty("id") final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserId userId = (UserId) o;
        return id == userId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserId{" +
                "id=" + id +
                '}';
    }

}
