package net.openblazar.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum CxlRejReason {

    TooLateToCancel("0", "Too Late To Cancel"),
    UnknownOrder("1", "Unknown Order"),
    BrokerOption("2", "Broker Option"),
    OrderPending("3", "Order already in Pending Cancel or Pending Replace status"),
    UnableToProcess("4", "Unable to process Order Mass Cancel Request"),
    OrigOrdModTime("5", "OrigOrdModTime (586) did not match last TransactTime (60) of order"),
    DuplicateClOrdID("6", "Duplicate ClOrdID (11) received"),
    PriceExceedsCurrentPrice("7", "Price exceeds current price"),
    PriceExceedsCurrentPriceBand("8", "Price exceeds current price band"),
    InvalidPriceIncrement("18", "Invalid price increment"),
    Other("99", "Other"),
    Unknown("", "");

    private final String value;
    private final String description;

    CxlRejReason(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (CxlRejReason cxlRejReason : values()) {
            if (cxlRejReason.getValue().equals(value)) {
                return cxlRejReason.getDescription();
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
