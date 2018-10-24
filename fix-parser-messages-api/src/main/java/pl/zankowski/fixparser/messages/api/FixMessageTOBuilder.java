package pl.zankowski.fixparser.messages.api;

import java.util.List;

public final class FixMessageTOBuilder {
    private Long messageId;
    private FixVersion version;
    private FixPairTO messageType;
    private List<FixPairTO> messageFields;

    public FixMessageTOBuilder withMessageId(Long messageId) {
        this.messageId = messageId;
        return this;
    }

    public FixMessageTOBuilder withVersion(FixVersion version) {
        this.version = version;
        return this;
    }

    public FixMessageTOBuilder withMessageType(FixPairTO messageType) {
        this.messageType = messageType;
        return this;
    }

    public FixMessageTOBuilder withMessageFields(List<FixPairTO> messageFields) {
        this.messageFields = messageFields;
        return this;
    }

    public FixMessageTO build() {
        return new FixMessageTO(messageId, version, messageType, messageFields);
    }
}
