package pl.zankowski.fixparser.messages.api;

public final class FixFieldTOBuilder {
    private int tag;
    private String name;

    public FixFieldTOBuilder withTag(int tag) {
        this.tag = tag;
        return this;
    }

    public FixFieldTOBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FixFieldTO build() {
        return new FixFieldTO(tag, name);
    }
}
