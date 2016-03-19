package net.openblazar.bfp.fix.data.field;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public enum MsgType {

    Heartbeat("0", "Heartbeat"),
    TestRequest("1", "Test Request"),
    ResendRequest("2", "Resend Request"),
    Reject("3", "Reject"),
    SequenceReset("4", "Sequence Reset"),
    Logout("5", "Logout"),
    IndicationOfInterest("6", "Indication of Interest"),
    Advertisement("7", "Advertisement"),
    ExecutionReport("8", "Execution Report"),
    OrderCancelReject("9", "Order Cancel Reject"),
    Logon("A", "Logon"),
    DerivativeSecurityList("AA", "Derivative Security List"),
    NewOrderMultileg("AB", "New Order Multileg"),
    MultilegOrderCancelReplace("AC", "Multileg Order Cancel Replace"),
    TradeCaptureReportRequest("AD", "Trade Capture Report Request"),
    TradeCaptureReport("AE", "Trade Capture Report"),
    OrderMassStatusRequest("AF", "Order Mass Status Request"),
    QuoteRequestReject("AG", "Quote Request Reject"),
    RFQRequest("AH", "RFQ Request"),
    QuoteStatusReport("AI", "Quote Status Report"),
    QuoteResponse("AJ", "QuoteResponse"),
    Confirmation("AK", "Confirmation"),
    PositionMaintenanceRequest("AL", "Position Maintenance Request"),
    PositionMaintenanceReport("AM", "Position Maintenance Report"),
    RequestForPositions("AN", "Request For Positions"),
    RequestForPositionsAck("AO", "Request For Positions Ack"),
    PositionReport("AP", "Position Report"),
    TradeCaptureReportRequestAck("AQ", "Trade Capture Report Request Ack"),
    TradeCaptureReportAck("AR", "Trade Capture Report Ack"),
    AllocationReport("AS", "Allocation Report"),
    AllocationReportAck("AT", "Allocation Report Ack"),
    ConfirmationAck("AU", "Confirmation Ack"),
    SettlementInstructionRequest("AV", "Settlement Instruction Request"),
    AssignmentReport("AW", "Assignment Report"),
    CollateralRequest("AX", "Collateral Request"),
    CollateralAssignment("AY", "Collateral Assignment"),
    CollateralResponse("AZ", "CollateralResponse"),
    News("B", "News"),
    CollateralReport("BA", "Collateral Report"),
    CollateralInquiry("BB", "Collateral Inquiry"),
    NetworkCounterpartySystemStatusRequest("BC", "Network Counterparty System Status Request"),
    NetworkCounterpartySystemStatusResponse("BD", "NetworkCounterpartySystemStatusResponse"),
    UserRequest("BE", "User Request"),
    UserResponse("BF", "User Response"),
    CollateralInquiryAck("BG", "Collateral Inquiry Ack"),
    ConfirmationRequest("BH", "Confirmation Request"),
    TradingSessionListRequest("BI", "Trading Session List Request"),
    TradingSessionList("BJ", "Trading Session List"),
    SecurityListUpdateReport("BK", "Security List Update Report"),
    AdjustedPositionReport("BL", "Adjusted Position Report"),
    AllocationInstructionAlert("BM", "Allocation Instruction Alert"),
    ExecutionAcknowledgement("BN", "Execution Acknowledgement"),
    ContraryIntentionReport("BO", "Contrary Intention Report"),
    SecurityDefinitionUpdateReport("BP", "Security Definition Update Report"),
    Email("C", "Email"),
    NewOrderSingle("D", "New Order Single"),
    NewOrderList("E", "New Order List"),
    OrderCancelRequest("F", "Order Cancel Request"),
    OrderCancelReplaceRequest("G", "Order Cancel/Replace Request"),
    OrderStatusRequest("H", "Order Status Request"),
    AllocationInstruction("J", "Allocation"),
    ListCancelRequest("K", "List Cancel Request"),
    ListExecute("L", "List Execute"),
    ListStatusRequest("M", "List Status Request"),
    ListStatus("N", "List Status"),
    AllocationInstructionACK("P", "Allocation ACK"),
    DontKnowTradeDK("Q", "Don't Know Trade"),
    QuoteRequest("R", "Quote Request"),
    Quote("S", "Quote"),
    SettlementInstructions("T", "Settlement Instructions"),
    MarketDataRequest("V", "Market Data Request"),
    MarketDataSnapshotFullRefresh("W", "Market Data - Snapshot"),
    MarketDataIncrementalRefresh("X", "Market Data - Incremental"),
    MarketDataRequestReject("Y", "Market Data Request Reject"),
    QuoteCancel("Z", "Quote Cancel"),
    QuoteStatusRequest("a", "Quote Status Request"),
    MassQuoteAcknowledgement("b", "Quote Acknowledgement"),
    SecurityDefinitionRequest("c", "Security Definition Request"),
    SecurityDefinition("d", "Security Definition"),
    SecurityStatusRequest("e", "Security Status Request"),
    SecurityStatus("f", "Security Status"),
    TradingSessionStatusRequest("g", "Trading Session Status Request"),
    TradingSessionStatus("h", "Trading Session Status"),
    MassQuote("i", "Mass Quote"),
    BusinessMessageReject("j", "Business Message Reject"),
    BidRequest("k", "Bid Request"),
    BidResponse("l", "Bid Response"),
    ListStrikePrice("m", "List Strike Price"),
    XMLnonFIX("n", "XML non FIX"),
    RegistrationInstructions("o", "Registration Instructions"),
    RegistrationInstructionsResponse("p", "Registration Instructions Response"),
    OrderMassCancelRequest("q", "Order Mass Cancel Request"),
    OrderMassCancelReport("r", "Order Mass Cancel Report"),
    NewOrderCross("s", "New Order Cross"),
    CrossOrderCancelReplaceRequest("t", "Cross Order Cancel Replace Request"),
    SecurityTypeRequest("v", "Security Type Request"),
    SecurityTypes("w", "Security Types"),
    SecurityListRequest("x", "Security List Request"),
    SecurityList("y", "Security List"),
    DerivativeSecurityListRequest("z", "Derivative Security List Request"),
    Unknown("", "");

    private static final Map<String, MsgType> map;

    static {
        map = new HashMap<>();
        for (MsgType msgType : values()) {
            map.put(msgType.getValue(), msgType);
        }
    }

    private final String value;
    private final String description;

    MsgType(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static MsgType getMsgTypeFromValue(String value) {
        MsgType msgType = map.get(value);
        if (msgType == null) {
            return Unknown;
        }
        return msgType;
    }

    public static String getDescriptionFromValue(String value) {
        return getMsgTypeFromValue(value).name();
    }

}
