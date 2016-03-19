package net.openblazar.bfp.data.fix.field;

/**
 * @author Wojciech Zankowski
 */
public enum ExecTransType {

    New("0"),
    Cancel("1"),
    Correct("2"),
    Status("3"),
    Unknown("");

    private final String value;

    ExecTransType(String value) {
        this.value = value;
    }

    public static String getDescriptionFromValue(String value) {
        for (ExecTransType execTransType : values()) {
            if (execTransType.getValue().equals(value)) {
                return execTransType.name();
            }
        }
        return Unknown.name();
    }

    public String getValue() {
        return value;
    }
}
