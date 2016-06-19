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
package com.blazarquant.bfp.fix.data;

/**
 * @author Wojciech Zankowski
 */
public class FixValue {

    private final String value;
    private final String description;

    public FixValue(String value) {
        this(value, "");
    }

    public FixValue(String value, String description) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixValue fixValue = (FixValue) o;

        if (getValue() != null ? !getValue().equals(fixValue.getValue()) : fixValue.getValue() != null) return false;
        return getDescription() != null ? getDescription().equals(fixValue.getDescription()) : fixValue.getDescription() == null;

    }

    @Override
    public int hashCode() {
        int result = getValue() != null ? getValue().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixValue{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
