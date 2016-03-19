package net.openblazar.bfp.fix.parser.util;

/**
 * @author Wojciech Zankowski
 */
public class FixTestConstants {

    public static final String LEGAL_FIX_1 = "8=FIX.4.4|9=12|35=A|34=1|49=EXEC";
    public static final String LEGAL_FIX_2 = "8=FIX.4.2#9=197#35=8#6=0.0#11=373009#14=0#17=373009";
    public static final String LEGAL_FIX_3 = "10/11/15 12:23:49.729  INFO FIX.4.2:SENDER->ACCEPTOR: 8=FIX.4.2#9=255#35=8#34=126";
    public static final String LEGAL_FIX_4 = "8=FIX.4.2\u00019=197\u000135=8\u00016=0.0\u000111=373009\u000114=0\u000117=373009";

    public static final String ILLEGAL_FIX_1 = "";
    public static final String ILLEGAL_FIX_2 = "8=FIX.4.2#6=0.0#11=373009#14=0#17=373009";
    public static final String ILLEGAL_FIX_3 = "10/11/15 12:23:49.729  INFO FIX.4.2:SENDER->ACCEPTOR:";
    public static final String ILLEGAL_FIX_4 = "8=FIX.4.2#49=197#135=8#6=0.0#11=373009#14=0#17=373009";
    public static final String ILLEGAL_FIX_5 = "8=FIX.4.2#9=197#135=8#6=0.0#11=373009#14=0#17=373009";

    public static final String EDGE_CASE_FIX = "Edge Case9=32 8=FIX.4.4|9=12|35=A|34=1|49=EXEC";

    public static final String LEGAL_FIX_LONG_1 = "8=FIX.4.2#9=198#35=8#6=41.21#10=024";
    public static final String LEGAL_FIX_LONG_2 = "8=FIX.4.2#9=204#35=8#6=102.75#11=38400195#10=152";
    public static final String LEGAL_FIX_LONG_3 = "8=FIX.4.2#9=198#35=8#6=41.21#11=3840019#14=102#10=024";
    public static final String LEGAL_FIX_MANY = "TEST " + LEGAL_FIX_LONG_1 + "#" + LEGAL_FIX_LONG_2 + "#" + LEGAL_FIX_LONG_3 + "# TEST";

}
