package pl.zankowski.fixparser.web.model.field;

public enum OrdType {

    Market("1", "Market"),
    Limit("2", "Limit"),
    Stop("3", "Stop"),
    Stop_Limit("4", "Stop Limit"),
    Market_on_close("5", "Market on close"),
    With_or_without("6", "With or without"),
    Limit_or_better("7", "Limit or better"),
    Limit_with_or_without("8", "Limit with or without"),
    On_basis("9", "On basis"),
    On_close("A", "On close"),
    Limit_on_close("B", "Limit on close"),
    Forex("C", "Forex"),
    Previously_quoted("D", "Previously quoted"),
    Previously_indicated("E", "Previously indicated"),
    Forex_Limit("F", "Forex - Limit"),
    Forex_Swap("G", "Forex - Swap"),
    Forex_Quoted("H", "Forex - Previously Quoted"),
    Funari("I", "Funari"),
    MarketIfTouched("J", "Market If Touched"),
    MarketWithLeftOver("K", "Market With Left Over as Limit "),
    PreviousFundValuation("L", "Previous Fund Valuation Point"),
    NextFundValuation("M", "Next Fund Valuation Point"),
    Pegged("P", "Pegged"),
    CounterOrder("Q", "Counter-order selection"),
    Unknown("", "");

    public final String value;
    public final String description;

    OrdType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public static String getDescriptionFromValue(String value) {
        for (OrdType ordType : values()) {
            if (ordType.getValue().equals(value)) {
                return ordType.getDescription();
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
