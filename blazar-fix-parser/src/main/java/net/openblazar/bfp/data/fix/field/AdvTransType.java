package net.openblazar.bfp.data.fix.field;

/**
 * @author Wojciech Zankowski
 */
public enum AdvTransType {

    New("N"),
    Cancel("C"),
    Replace("R"),
    Unknown("");

    private final String value;

    AdvTransType(String value) {
        this.value = value;
    }

    public static String getDescriptionFromValue(String value) {
        for (AdvTransType advTransType : values()) {
            if (advTransType.getValue().equals(value)) {
                return advTransType.name();
            }
        }
        return Unknown.name();
    }

    public String getValue() {
        return value;
    }
}
