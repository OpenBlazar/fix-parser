package net.openblazar.bfp.data.fix;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Wojciech Zankowski
 */
public class FixMessage {

    private final FixVersion version;
    private final FixMessageType messageType;
    private final Map<FixField, String> messageFields;

    public FixMessage(FixVersion version, FixMessageType messageType, Map<FixField, String> messageFields) {
        Objects.nonNull(version);
        Objects.nonNull(messageType);
        Objects.nonNull(messageFields);

        this.version = version;
        this.messageType = messageType;
        this.messageFields = messageFields;
    }

    public void addField(FixField field, String value) {
        messageFields.put(field, value);
    }

    public FixMessageType getMessageType() {
        return messageType;
    }

    public Map<FixField, String> getMessageFields() {
        return messageFields;
    }

    public Optional<String> getField(FixField field) {
        return Optional.ofNullable(messageFields.get(field));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixMessage that = (FixMessage) o;

        if (version != that.version) return false;
        if (messageType != that.messageType) return false;
        return messageFields != null ? messageFields.equals(that.messageFields) : that.messageFields == null;
    }

    @Override
    public int hashCode() {
        int result = version != null ? version.hashCode() : 0;
        result = 31 * result + (messageType != null ? messageType.hashCode() : 0);
        result = 31 * result + (messageFields != null ? messageFields.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FixMessage{" +
                "messageType=" + messageType +
                ", messageFields=" + messageFields +
                '}';
    }

    public static class Builder {

        private FixVersion version = FixVersion.UNKNOWN;
        private FixMessageType messageType = FixMessageType.UNKNOWN;
        private Map<FixField, String> messageFields = new HashMap<>();

        public Builder() {}

        public Builder version(FixVersion version) {
            this.version = version;
            return this;
        }

        public Builder messageType(FixMessageType messageType) {
            this.messageType = messageType;
            return this;
        }

        public Builder messageFields(Map<FixField, String> messageFields) {
            this.messageFields = messageFields;
            return this;
        }

        public FixMessage build() {
            return new FixMessage(version, messageType, messageFields);
        }

    }

}
