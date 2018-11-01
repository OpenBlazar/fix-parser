package pl.zankowski.fixparser.messages.entity.dictionary;

import com.google.common.collect.Maps;

import java.util.Map;

public final class FixFieldDefinitionBuilder {
    private FixField field;
    private Map<String, String> values = Maps.newHashMap();

    public FixFieldDefinitionBuilder withField(final FixField field) {
        this.field = field;
        return this;
    }

    public FixFieldDefinitionBuilder withValues(final Map<String, String> values) {
        this.values = values;
        return this;
    }

    public FixFieldDefinitionBuilder withValue(final String name, final String description) {
        this.values.put(name, description);
        return this;
    }


    public FixFieldDefinition build() {
        return new FixFieldDefinition(field, values);
    }
}
