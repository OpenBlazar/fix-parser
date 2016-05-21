package com.blazarquant.bfp.fix.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
public class FixMessage {

    private final Long messageID;
    private final FixVersion version;
    private final FixPair messageType;
    private final List<FixPair> messageFields;

    public FixMessage(Long messageID, FixVersion version, FixPair messageType, List<FixPair> messageFields) {
        Objects.requireNonNull(messageID);
        Objects.requireNonNull(version);
        Objects.requireNonNull(messageType);
        Objects.requireNonNull(messageFields);

        this.messageID = messageID;
        this.version = version;
        this.messageType = messageType;
        this.messageFields = messageFields;
    }

    public Long getMessageID() {
        return messageID;
    }

    public FixPair getMessageType() {
        return messageType;
    }

    public FixVersion getVersion() {
        return version;
    }

    public List<FixPair> getMessageFields() {
        return messageFields;
    }

    public List<FixValue> getField(int tag) {
        return messageFields.stream()
                .filter(pair -> pair.getFixField().getTag() == tag)
                .map(pair -> pair.getFixValue())
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixMessage that = (FixMessage) o;

        if (messageID != null ? !messageID.equals(that.messageID) : that.messageID != null) return false;
        if (version != that.version) return false;
        if (messageType != null ? !messageType.equals(that.messageType) : that.messageType != null) return false;
        return messageFields != null ? messageFields.equals(that.messageFields) : that.messageFields == null;

    }

    @Override
    public int hashCode() {
        int result = messageID != null ? messageID.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (messageFields != null ? messageFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixMessage{" +
                "messageID=" + messageID +
                ", version=" + version +
                ", messageType=" + messageType +
                ", messageFields=" + messageFields +
                '}';
    }

    public static class Builder {

        private Long messageID = -1L;
        private FixVersion version = FixVersion.UNKNOWN;
        private FixPair messageType = FixPair.UNKNOWN;
        private List<FixPair> messageFields = new ArrayList<>();

        public Builder() {
        }

        public Builder messageID(Long messageID) {
            this.messageID = messageID;
            return this;
        }

        public Builder version(FixVersion version) {
            this.version = version;
            return this;
        }

        public Builder messageType(FixPair messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder messageFields(List<FixPair> messageFields) {
            this.messageFields = messageFields;
            return this;
        }

        public FixMessage build() {
            return new FixMessage(messageID, version, messageType, messageFields);
        }

    }

}
