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
public class FixPair {

    public static final FixPair UNKNOWN = new FixPair(0, new FixField(0, "Unknown"), new FixValue("", "Unknown"));

    private final int fixFieldTag;
    private final FixField fixField;
    private final FixValue fixValue;

    public FixPair(int fixFieldTag, FixField fixField, FixValue fixValue) {
        this.fixFieldTag = fixFieldTag;
        this.fixField = fixField;
        this.fixValue = fixValue;
    }

    public int getFixFieldTag() {
        return fixFieldTag;
    }

    public FixField getFixField() {
        return fixField;
    }

    public FixValue getFixValue() {
        return fixValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixPair fixPair = (FixPair) o;

        if (fixFieldTag != fixPair.fixFieldTag) return false;
        if (fixField != null ? !fixField.equals(fixPair.fixField) : fixPair.fixField != null) return false;
        return fixValue != null ? fixValue.equals(fixPair.fixValue) : fixPair.fixValue == null;

    }

    @Override
    public int hashCode() {
        int result = fixFieldTag;
        result = 31 * result + (fixField != null ? fixField.hashCode() : 0);
        result = 31 * result + (fixValue != null ? fixValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixPair{" +
                "fixFieldTag=" + fixFieldTag +
                ", fixField=" + fixField +
                ", fixValue=" + fixValue +
                '}';
    }
}
