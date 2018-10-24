package pl.zankowski.fixparser.messages.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import pl.zankowski.fixparser.core.ITransferObject;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonPropertyOrder({"messageId", "version", "messageType", "messageFields"})
public class FixMessageTO implements ITransferObject {

    private static final long serialVersionUID = 4019543823203872806L;

    private final Long messageId;
    private final FixVersion version;
    private final FixPairTO messageType;
    private final List<FixPairTO> messageFields;

    @JsonCreator
    public FixMessageTO(
            @JsonProperty("messageId") final Long messageId,
            @JsonProperty("version") final FixVersion version,
            @JsonProperty("messageType") final FixPairTO messageType,
            @JsonProperty("messageFields") final List<FixPairTO> messageFields) {
        this.messageId = messageId;
        this.version = version;
        this.messageType = messageType;
        this.messageFields = ImmutableList.copyOf(messageFields);
    }

    public Long getMessageId() {
        return messageId;
    }

    public FixPairTO getMessageType() {
        return messageType;
    }

    public FixVersion getVersion() {
        return version;
    }

    public List<FixPairTO> getMessageFields() {
        return messageFields;
    }

    public List<FixValueTO> getField(int tag) {
        return messageFields.stream()
                .filter(pair -> pair.getFixField().getTag() == tag)
                .map(pair -> pair.getFixValue())
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final FixMessageTO that = (FixMessageTO) o;
        return Objects.equals(messageId, that.messageId) &&
                Objects.equals(version, that.version) &&
                Objects.equals(messageType, that.messageType) &&
                Objects.equals(messageFields, that.messageFields);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, version, messageType, messageFields);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("messageId", messageId)
                .add("version", version)
                .add("messageType", messageType)
                .add("messageFields", messageFields)
                .toString();
    }
}
