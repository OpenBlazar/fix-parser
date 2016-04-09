package com.blazarquant.bfp.fix.data;

import com.blazarquant.bfp.fix.data.field.MsgType;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Wojciech Zankowski
 */
public class FixMessage {

    private final Long messageID;
    private final FixVersion version;
    private final MsgType messageType;
    private final List<FixPair> messageFields;

    public FixMessage(Long messageID, FixVersion version, MsgType messageType, List<FixPair> messageFields) {
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

    public MsgType getMessageType() {
        return messageType;
    }

    public FixVersion getVersion() {
        return version;
    }

    public List<FixPair> getMessageFields() {
        return messageFields;
    }

    public List<FixValue> getField(FixField field) {
        return messageFields.stream()
                .filter(pair -> pair.getFixField().equals(field))
                .map(pair -> pair.getFixValue())
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixMessage that = (FixMessage) o;

        if (getMessageID() != null ? !getMessageID().equals(that.getMessageID()) : that.getMessageID() != null)
            return false;
        if (getVersion() != that.getVersion()) return false;
        if (getMessageType() != that.getMessageType()) return false;
        return getMessageFields() != null ? getMessageFields().equals(that.getMessageFields()) : that.getMessageFields() == null;
    }

    @Override
    public int hashCode() {
        int result = getMessageID() != null ? getMessageID().hashCode() : 0;
        result = 31 * result + (getVersion() != null ? getVersion().hashCode() : 0);
        result = 31 * result + (getMessageType() != null ? getMessageType().hashCode() : 0);
        result = 31 * result + (getMessageFields() != null ? getMessageFields().hashCode() : 0);
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
        private MsgType messageType = MsgType.Unknown;
        private List<FixPair> messageFields = new ArrayList<>();

        public Builder() {}

        public Builder messageID(Long messageID) {
            this.messageID = messageID;
            return this;
        }

        public Builder version(FixVersion version) {
            this.version = version;
            return this;
        }

        public Builder messageType(MsgType messageType) {
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
