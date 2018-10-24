package pl.zankowski.fixparser.messages.api;

public final class FixValueTOBuilder {
    private String value;
    private String description = "";

    public FixValueTOBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public FixValueTOBuilder withNoDescription() {
        this.description = "";
        return this;
    }

    public FixValueTOBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public FixValueTO build() {
        return new FixValueTO(value, description);
    }
}
