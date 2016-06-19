/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
