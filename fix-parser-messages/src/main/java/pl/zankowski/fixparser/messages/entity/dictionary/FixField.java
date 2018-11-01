package pl.zankowski.fixparser.messages.entity.dictionary;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.util.Objects;

public class FixField implements IEntity {

    private static final long serialVersionUID = 8784537477337622848L;

    private final int tag;
    private final String name;

    public FixField(final int tag, final String name) {
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
        final FixField that = (FixField) o;
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
