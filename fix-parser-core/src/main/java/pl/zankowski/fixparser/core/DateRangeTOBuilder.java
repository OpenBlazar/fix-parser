package pl.zankowski.fixparser.core;

import java.time.LocalDate;

public final class DateRangeTOBuilder {
    private LocalDate from;
    private LocalDate to;

    public DateRangeTOBuilder withFrom(LocalDate from) {
        this.from = from;
        return this;
    }

    public DateRangeTOBuilder withTo(LocalDate to) {
        this.to = to;
        return this;
    }

    public DateRangeTO build() {
        return new DateRangeTO(from, to);
    }
}
