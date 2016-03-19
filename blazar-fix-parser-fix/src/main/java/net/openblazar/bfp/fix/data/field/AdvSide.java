package net.openblazar.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum AdvSide {

    Buy("B"),
    Sell("S"),
    Cross("X"),
    Trade("T"),
    Unkown("");

    private final String value;

    AdvSide(String value) {
        this.value = value;
    }

    public static String getDescriptionFromValue(String value) {
        for (AdvSide advSide : values()) {
            if (advSide.getValue().equals(value)) {
                return advSide.getValue();
            }
        }
        return Unkown.getValue();
    }

    public String getValue() {
        return value;
    }
}
