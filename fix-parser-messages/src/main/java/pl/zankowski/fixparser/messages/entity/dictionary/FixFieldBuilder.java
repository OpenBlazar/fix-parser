package pl.zankowski.fixparser.messages.entity.dictionary;

public final class FixFieldBuilder {
    private int tag;
    private String name;

    public FixFieldBuilder withTag(final int tag) {
        this.tag = tag;
        return this;
    }

    public FixFieldBuilder withName(final String name) {
        this.name = name;
        return this;
    }

    public FixField build() {
        return new FixField(tag, name);
    }
}
