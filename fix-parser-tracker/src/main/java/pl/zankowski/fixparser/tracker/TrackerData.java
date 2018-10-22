package pl.zankowski.fixparser.tracker;

import pl.zankowski.fixparser.core.entity.IEntity;

import java.time.Instant;
import java.util.Objects;

public class TrackerData implements IEntity {

    private static final long serialVersionUID = -7509917142733513185L;

    private final Instant parseDate;
    private final int messageNumber;

    public TrackerData(final Instant parseDate, final int messageNumber) {
        Objects.requireNonNull(parseDate);

        this.parseDate = parseDate;
        this.messageNumber = messageNumber;
    }

    public Instant getParseDate() {
        return parseDate;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TrackerData that = (TrackerData) o;
        return messageNumber == that.messageNumber &&
                Objects.equals(parseDate, that.parseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parseDate, messageNumber);
    }

    @Override
    public String toString() {
        return "TrackerData{" +
                "parseDate=" + parseDate +
                ", messageNumber=" + messageNumber +
                '}';
    }

}
