package pl.zankowski.fixparser.user.api;

public final class UserIdTOBuilder {
    private long id;

    public UserIdTOBuilder withId(final long id) {
        this.id = id;
        return this;
    }

    public UserIdTO build() {
        return new UserIdTO(id);
    }
}
