package pl.zankowski.fixparser.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.util.Objects;

@JsonPropertyOrder({"from", "to"})
public class DateRangeTO implements ITransferObject {

    private static final long serialVersionUID = 1583595192532563127L;

    private final LocalDate from;
    private final LocalDate to;

    @JsonCreator
    public DateRangeTO(
            @JsonProperty("from") final LocalDate from,
            @JsonProperty("to") final LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DateRangeTO that = (DateRangeTO) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return "DateRangeTO{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

}
