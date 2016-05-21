package com.blazarquant.bfp.fix.data.definition;

import com.blazarquant.bfp.fix.parser.definition.loader.QuickFixXMLLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class FixDictionary {

    private final int tag;
    private final String name;
    private final Map<String, String> values;

    public FixDictionary(int tag, String name, Map<String, String> values) {
        this.tag = tag;
        this.name = name;
        this.values = values;
    }

    public int getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixDictionary that = (FixDictionary) o;

        if (tag != that.tag) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = tag;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixDictionary{" +
                "tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                ", values=" + values +
                '}';
    }

    public static class Builder {

        private int tag;
        private String name;
        private Map<String, String> values = new HashMap<>();

        public Builder tag(int tag) {
            this.tag = tag;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder value(String valueEnum, String description) {
            values.put(valueEnum, description);
            return this;
        }

        public FixDictionary build() {
            return new FixDictionary(tag, name, values);
        }

    }

}
