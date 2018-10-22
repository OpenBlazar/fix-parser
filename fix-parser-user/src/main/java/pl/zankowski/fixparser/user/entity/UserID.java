package pl.zankowski.fixparser.user.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserID implements Serializable {

    private static final long serialVersionUID = 1926039666988035924L;

    private final long id;

    public UserID(final long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserID userID = (UserID) o;
        return id == userID.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "UserID{" +
                "id=" + id +
                '}';
    }

}
