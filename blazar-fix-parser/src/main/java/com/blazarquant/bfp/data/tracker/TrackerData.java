package com.blazarquant.bfp.data.tracker;

import java.time.Instant;
import java.util.Objects;

/**
 * @author Wojciech Zankowski
 */
public class TrackerData {

    private final Instant parseDate;
    private final int messageNumber;

    public TrackerData(Instant parseDate, int messageNumber) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrackerData that = (TrackerData) o;

        if (getMessageNumber() != that.getMessageNumber()) return false;
        return getParseDate() != null ? getParseDate().equals(that.getParseDate()) : that.getParseDate() == null;

    }

    @Override
    public int hashCode() {
        int result = getParseDate() != null ? getParseDate().hashCode() : 0;
        result = 31 * result + getMessageNumber();
        return result;
    }

    @Override
    public String toString() {
        return "TrackerData{" +
                "parseDate=" + parseDate +
                ", messageNumber=" + messageNumber +
                '}';
    }

}
