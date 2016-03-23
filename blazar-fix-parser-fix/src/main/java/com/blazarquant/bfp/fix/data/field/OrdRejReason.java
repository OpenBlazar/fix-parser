package com.blazarquant.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum OrdRejReason {

    BrokerOption("0", "Broker Option"),
    UnknownSymbol("1", "Unknown Symbol"),
    ExchangeClosed("2", "Exchange Closed"),
    OrderExceedsLimit("3", "Order Exceeds Limit"),
    TooLateToEnter("4", "Too Late To Enter"),
    UnknownOrder("5", "Unknown Order"),
    Duplicate("6", "Duplicate Order"),
    DuplicateOrder("7", "Duplicate of a verbally communicated order"),
    StaleOrder("8", "Stale Order"),
    TradeAlongRequired("9", "Trade along required"),
    InvalidInvestorID("10", "Invalid Investor ID"),
    UnsupportedOrderCharacteristics("11", "UnsupportedOrderCharacteristics"),
    SurveillenceOption("12", "Surveillence Option"),
    IncorrectQuantity("13", "Incorrect Qunatity"),
    IncorrectAllocatedQunatity("14", "Incorrect allocated quantity"),
    UnknownAccounts("15", "Unknown account(s)"),
    PriceExceedsCurrentPriceBand("16", "Price exceeds current price band"),
    InvalidPriceIncrement("18", "Invalid price increment"),
    Other("99", "Other"),
    Unknown("", "");

    private final String value;
    private final String description;

    OrdRejReason(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (OrdRejReason ordRejReason : values()) {
            if (ordRejReason.getValue().equals(value)) {
                return ordRejReason.getDescription();
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
