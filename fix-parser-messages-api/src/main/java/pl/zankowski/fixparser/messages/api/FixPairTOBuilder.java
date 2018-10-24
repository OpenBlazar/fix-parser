package pl.zankowski.fixparser.messages.api;

public final class FixPairTOBuilder {
    private Integer fixFieldTag;
    private FixFieldTO fixField;
    private FixValueTO fixValue;

    public FixPairTOBuilder withFixFieldTag(Integer fixFieldTag) {
        this.fixFieldTag = fixFieldTag;
        return this;
    }

    public FixPairTOBuilder withFixField(FixFieldTO fixField) {
        this.fixField = fixField;
        return this;
    }

    public FixPairTOBuilder withFixValue(FixValueTO fixValue) {
        this.fixValue = fixValue;
        return this;
    }

    public FixPairTO build() {
        return new FixPairTO(fixFieldTag, fixField, fixValue);
    }
}
