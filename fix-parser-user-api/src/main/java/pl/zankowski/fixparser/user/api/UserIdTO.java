package pl.zankowski.fixparser.user.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.Objects;

@JsonPropertyOrder({"id"})
public class UserIdTO implements ITransferObject {

    private static final long serialVersionUID = -4248121226872487818L;

    private final long id;

    @JsonCreator
    public UserIdTO(
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
        final UserIdTO userIdTO = (UserIdTO) o;
        return id == userIdTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserIdTO{" +
                "id=" + id +
                '}';
    }

}
