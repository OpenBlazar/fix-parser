/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.fix.data.definition;

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
