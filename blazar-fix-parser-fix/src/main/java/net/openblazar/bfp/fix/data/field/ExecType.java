package net.openblazar.bfp.fix.data.field;

/**
 * @author Wojciech Zankowski
 */
public enum ExecType {

    New("0", "New"),
    PartialFill("1", "Partial Fill"),
    Fill("2", "Fill"),
    DoneForDay("3", "Done for day"),
    Cancelled("4", "Cancelled"),
    Replace("5", "Replace"),
    PendingCancel("6", "Pending Cancel"),
    Stopped("7", "Stopped"),
    Rejected("8", "Rejected"),
    Suspended("9", "Suspended"),
    PendingNew("A", "Pending New"),
    Calculated("B", "Calculated"),
    Expired("C", "Expired"),
    Restated("D", "Restated"),
    PendingReplace("E", "Pending Replace"),
    Trade("F", "Trade (partial fill or fill)"),
    TradeCorrect("G", "Trade Correct"),
    TradeCancel("H", "Trade Cancel"),
    OrderStatus("I", "Order Status"),
    TradeClearingHold("J", "Trade in a Clearing Hold"),
    TradeReleasedClearing("K", "Trade has been released to Clearing"),
    TriggeredBySystem("L", "Triggered or Activated by System"),
    Unknown("", "");

    private final String value;
    private final String description;

    ExecType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (ExecType execType : values()) {
            if (execType.getValue().equals(value)) {
                return execType.getDescription();
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
