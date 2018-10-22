package pl.zankowski.fixparser.tracker.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import pl.zankowski.fixparser.core.ITransferObject;

import java.time.Instant;
import java.util.Objects;

@JsonPropertyOrder({"parseDate", "messageNumber"})
public class TrackerDataTO implements ITransferObject {

    private static final long serialVersionUID = -8406811067354907600L;

    private final Instant parseDate;
    private final Integer messageNumber;

    @JsonCreator
    public TrackerDataTO(
            @JsonProperty("parseDate") final Instant parseDate,
            @JsonProperty("messageNumber") final Integer messageNumber) {
        this.parseDate = parseDate;
        this.messageNumber = messageNumber;
    }

    public Instant getParseDate() {
        return parseDate;
    }

    public Integer getMessageNumber() {
        return messageNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TrackerDataTO that = (TrackerDataTO) o;
        return Objects.equals(parseDate, that.parseDate) &&
                Objects.equals(messageNumber, that.messageNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parseDate, messageNumber);
    }

    @Override
    public String toString() {
        return "TrackerDataTO{" +
                "parseDate=" + parseDate +
                ", messageNumber=" + messageNumber +
                '}';
    }

}
