package com.blazarquant.bfp.fix.data.field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public enum ExecInst {

    NotHeld("1", "Not Held"),
    Work("2", "Work"),
    GoAlong("3", "Go along"),
    OverTheDay("4", "Over the day"),
    Held("5", "Held"),
    ParticipateDontInitiate("6", "Participate don't initiate"),
    StrictScale("7", "Strict scale"),
    TryToScale("8", "Try to scale"),
    StayOnBidside("9", "Stay on bidside"),
    StayOnOfferside("0", "Stay on offerside"),
    TrailingStopPrice("a", "Trailing Stop Peg"),
    StrictLimit("b", "Strict Limit"),
    IgnorePriceValidity("c", "Ignore Price Validity Checks"),
    PegToLimitPrice("d", "Peg To Limit Price"),
    WorkToTarget("e", "Work to Targe Strategy"),
    IntermarketSweep("f", "Intermarket Sweep"),
    SingleExecutionReqForBlockTrade("j", "Single execution requested for block trade"),
    ExternalRoutingAllowed("g", "External Routing Allowed"),
    ExternalRoutingNotAllowed("h", "External Routing Not Allowed"),
    ImbalanceOnly("i", "Imbalance Only"),
    BestExecution("k", "Best Execution"),
    SuspendOnFailure("l", "Suspend on system failure"),
    SuspendOnTradingHalt("m", "Suspend on Trading Halt"),
    ReinstateOnConnectionLoss("n", "Reinstate on connection loss"),
    CancelOnConnectionLoss("o", "Cancel on connection loss"),
    SuspendOnConnectionLoss("p", "Suspend on connection loss"),
    ReleaseFromSuspension("q", "Release from suspension"),
    ExecuteAsDelta("r", "Execute as delta neutral using volatility provided"),
    ExecuteAsNeutral("s", "Execute as duration neutral"),
    ExecuteAsFX("t", "Execute as FX neutral"),
    NoCross("A", "No cross"),
    OkToCross("B", "Ok to cross"),
    CallFirst("C", "Call first"),
    PercentOfVolume("D", "Percent of volume"),
    DoNotIncrease("E", "Do not increase"),
    DoNotReduce("F", "Do not reduce"),
    AllOrNone("G", "All or none"),
    InstitutionsOnly("I", "Institutions only"),
    LastPeg("L", "Last peg"),
    MidPricePeg("M", "Mid-price peg"),
    NonNegotiable("N", "Non-negotiable"),
    OpeningPeg("O", "Opening peg"),
    MarketPeg("P", "Market peg"),
    PrimaryPeg("R", "Primary peg"),
    Suspend("S", "Suspend"),
    CustomerInstruction("U", "Customer Display Instruction"),
    Netting("V", "Netting"),
    PegToVWAP("W", "Peg to VWAP"),
    TradeAlong("X", "Trade Along"),
    TryToStop("Y", "Try To Stop"),
    CancelIfNotBest("Z", "Cancel if not best"),
    Unknown("", "");

    private static final Map<String, ExecInst> map;

    static {
        map = new HashMap<>();
        for (ExecInst execInst : values()) {
            map.put(execInst.getValue(), execInst);
        }
    }

    private final String value;
    private final String description;

    ExecInst(String value, String description) {
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
        ExecInst execInst = map.get(value);
        if (execInst == null) {
            return Unknown.getDescription();
        }
        return execInst.getDescription();
    }
}
