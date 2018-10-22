package pl.zankowski.fixparser.tracker.api;

import java.time.Instant;

public final class TrackerDataTOBuilder {
    private Instant parseDate;
    private Integer messageNumber;

    public TrackerDataTOBuilder withParseDate(Instant parseDate) {
        this.parseDate = parseDate;
        return this;
    }

    public TrackerDataTOBuilder withMessageNumber(Integer messageNumber) {
        this.messageNumber = messageNumber;
        return this;
    }

    public TrackerDataTO build() {
        return new TrackerDataTO(parseDate, messageNumber);
    }
}
