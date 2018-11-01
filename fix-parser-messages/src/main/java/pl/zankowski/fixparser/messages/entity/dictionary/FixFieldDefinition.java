package pl.zankowski.fixparser.messages.entity.dictionary;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.util.Map;
import java.util.Objects;

public class FixFieldDefinition implements IEntity {

    private static final long serialVersionUID = 8245712429839352129L;

    private final FixField field;
    private final Map<String, String> values;

    public FixFieldDefinition(final FixField field, final Map<String, String> values) {
        this.field = field;
        this.values = values;
    }

    public FixField getField() {
        return field;
    }

    public Map<String, String> getValues() {
        return values;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixFieldDefinition that = (FixFieldDefinition) o;
        return Objects.equals(field, that.field) &&
                Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field, values);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("field", field)
                .add("values", values)
                .toString();
    }
}
