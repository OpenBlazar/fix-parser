package pl.zankowski.fixparser.messages.entity.parser;

import java.time.Instant;

public final class FixMessageBuilder {
    private String id;
    private Long userId;
    private Instant timestamp;
    private String input;

    public FixMessageBuilder withId(final String id) {
        this.id = id;
        return this;
    }

    public FixMessageBuilder withUserId(final Long userId) {
        this.userId = userId;
        return this;
    }

    public FixMessageBuilder withTimestamp(final Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public FixMessageBuilder withInput(final String input) {
        this.input = input;
        return this;
    }

    public FixMessage build() {
        return new FixMessage(id, userId, timestamp, input);
    }
}
