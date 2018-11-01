package pl.zankowski.fixparser.user.api;

public final class UserIdBuilder {
    private long id;

    public UserIdBuilder withId(final long id) {
        this.id = id;
        return this;
    }

    public UserId build() {
        return new UserId(id);
    }
}
