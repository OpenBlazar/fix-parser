package pl.zankowski.fixparser.messages.entity.parser;

import com.google.common.base.MoreObjects;
import pl.zankowski.fixparser.core.entity.IEntity;

import java.time.Instant;
import java.util.Objects;

public class FixMessage implements IEntity {

    private final String id;
    private final Long userId;
    private final Instant timestamp;
    private final String input;

    public FixMessage(final String id, final Long userId, final Instant timestamp, final String input) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.input = input;
    }

    public String getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getInput() {
        return input;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixMessage that = (FixMessage) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(input, that.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, timestamp, input);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("userId", userId)
                .add("timestamp", timestamp)
                .add("input", input)
                .toString();
    }
}
