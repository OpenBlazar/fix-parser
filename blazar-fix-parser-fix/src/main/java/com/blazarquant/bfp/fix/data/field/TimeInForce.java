package com.blazarquant.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum TimeInForce {

    Day("0", "Day"),
    GTC("1", "Good Till Cancel (GTC)"),
    OPG("2", "At the Opening (OPG)"),
    OC("3", "Immediate or Cancel (OC"),
    FOK("4", "Fill or Kill (FOK)"),
    GTX("5", "Good Till Crossing (GTX)"),
    GTD("6", "Good Till Date (GTD)"),
    AtTheClose("7", "At the Close"),
    GoodThroughCrossing("8", "Good Through Crossing"),
    AtCrossing("9", "At Crossing"),
    Unknown("", "");

    private final String value;
    private final String description;


    TimeInForce(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (TimeInForce timeInForce : values()) {
            if (timeInForce.getValue().equals(value)) {
                return timeInForce.getDescription();
            }
        }
        return Unknown.getDescription();
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
