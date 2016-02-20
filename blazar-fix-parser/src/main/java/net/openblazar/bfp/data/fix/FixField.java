package net.openblazar.bfp.data.fix;

/**
 * @author Wojciech Zankowski
 */
public enum FixField {

    AvgPx(6),
    BeginString(8),
    BodyLength(9),
    CheckSum(10),
    ClOrdID(11),
    CumQty(14),
    MsgType(35),
    Unknown(-1);

    private final int tag;

    FixField(int tag) {
        this.tag = tag;
    }

    public static FixField getFieldFromCode(int tag) {
        for (FixField field : values()) {
            if (field.getTag() == tag) {
                return field;
            }
        }
        return Unknown;
    }

    public int getTag() {
        return tag;
    }
}
