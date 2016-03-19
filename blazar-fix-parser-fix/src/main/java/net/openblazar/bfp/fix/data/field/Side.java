package net.openblazar.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum Side {

    Buy("1", "Buy"),
    Sell("2", "Sell"),
    BuyMinus("3", "Buy minus"),
    SellPlus("4", "Sell plus"),
    SellShort("5", "Sell short"),
    SellShortExempt("6", "Sell short exempt"),
    Undisclosed("7", "Undisclosed"),
    Cross("8", "Cross"),
    CrossShort("9", "Cross short"),
    CrossShortExempt("A", "Cross short exempt"),
    AsDefined("B", "\"As Defined\""),
    Opposite("C", "\"Opposite\""),
    Subscribe("D", "Subscribe"),
    Redeem("E", "Redeem"),
    Lend("F", "Lend"),
    Borrow("G", "Borrow"),
    Unknown("", "");

    private final String value;
    private final String description;

    Side(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (Side side : values()) {
            if (side.getValue().equals(value)) {
                return side.getDescription();
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
