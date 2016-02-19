package net.openblazar.bfp.data.fix;

/**
 * @author Wojciech Zankowski
 */
public enum FixMessageType {

    EXECUTION_REPORT(8);

    private final int code;

    FixMessageType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
