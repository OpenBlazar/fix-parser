package com.blazarquant.bfp.fix.data;

/**
 * @author Wojciech Zankowski
 */
public class FixPair {

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

        if (getFixFieldTag() != fixPair.getFixFieldTag()) return false;
        if (getFixField() != fixPair.getFixField()) return false;
        return getFixValue() != null ? getFixValue().equals(fixPair.getFixValue()) : fixPair.getFixValue() == null;

    }

    @Override
    public int hashCode() {
        int result = getFixFieldTag();
        result = 31 * result + (getFixField() != null ? getFixField().hashCode() : 0);
        result = 31 * result + (getFixValue() != null ? getFixValue().hashCode() : 0);
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
