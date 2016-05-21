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
