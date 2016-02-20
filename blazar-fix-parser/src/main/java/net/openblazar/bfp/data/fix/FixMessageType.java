package net.openblazar.bfp.data.fix;

/**
 * @author Wojciech Zankowski
 */
public enum FixMessageType {

    EXECUTION_REPORT(8),
    UNKNOWN(-1);

    private final int code;

    FixMessageType(int code) {
        this.code = code;
    }

    public static FixMessageType getMessageTypeFromCode(int code) {
        for (FixMessageType messageType : values()) {
            if (messageType.getCode() == code) {
                return messageType;
            }
        }
        return UNKNOWN;
    }

    public int getCode() {
        return code;
    }

}
