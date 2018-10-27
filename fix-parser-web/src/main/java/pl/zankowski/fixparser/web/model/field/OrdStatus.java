package pl.zankowski.fixparser.web.model.field;

public enum OrdStatus {

    New("0", "New"),
    Partially_Filled("1", "Partially Filled"),
    Filled("2", "Filled"),
    Done_for_day("3", "Done for day"),
    Canceled("4", "Canceled"),
    Replaced("5", "Replaced"),
    Pending_CancelReplace("6", "Pending Cancel/Replace"),
    Stopped("7", "Stopped"),
    Rejected("8", "Rejected"),
    Suspended("9", "Suspended"),
    Pending_New("A", "Pending New"),
    Calculated("B", "Calculated"),
    Expired("C", "Expired"),
    Bidding("D", "Accepted for bidding"),
    Pending_Replace("E", "Pending Replace"),
    Unknown("", "");

    private final String value;
    private final String description;

    OrdStatus(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionFromValue(String value) {
        return getOrdStatusFromValue(value).getDescription();
    }

    public static OrdStatus getOrdStatusFromValue(String value) {
        for (OrdStatus status : values()) {
            if (status.getValue().equals(value)) {
                return status;
            }
        }
        return Unknown;
    }
}
