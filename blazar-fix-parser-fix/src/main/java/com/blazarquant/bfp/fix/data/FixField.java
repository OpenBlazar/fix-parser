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
public class FixField {

    private final int tag;
    private final String name;

    public FixField(int tag, String name) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixField fixField2 = (FixField) o;

        if (tag != fixField2.tag) return false;
        return name != null ? name.equals(fixField2.name) : fixField2.name == null;

    }

    @Override
    public int hashCode() {
        int result = tag;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixField{" +
                "tag=" + tag +
                ", name='" + name + '\'' +
                '}';
    }

}
