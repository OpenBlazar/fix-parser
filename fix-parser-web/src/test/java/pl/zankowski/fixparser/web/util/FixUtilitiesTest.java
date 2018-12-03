package pl.zankowski.fixparser.web.util;

import org.junit.Ignore;
import org.junit.Test;
import pl.zankowski.fixparser.messages.api.FixFieldTO;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.FixMessageTOBuilder;
import pl.zankowski.fixparser.messages.api.FixPairTO;
import pl.zankowski.fixparser.messages.api.FixValueTO;
import pl.zankowski.fixparser.messages.fix.FixMessageFactory;

import static org.junit.Assert.assertEquals;

public class FixUtilitiesTest {

    private static final String SENDING_TIME = "2015/05/05 05:05:05";
    private static final String SENDER = "SENDER";
    private static final String TARGET = "TARGET";

    private final FixMessageFactory messageFactory = new FixMessageFactory();

    @Ignore
    @Test
    public void testSendingTimeUtilities() {
        FixMessageTO actualMessage_1 = messageFactory.createFixMessage(SENDING_TIME, SENDER, TARGET);
        FixMessageTO actualMessage_2 = new FixMessageTOBuilder().build();

        assertEquals(SENDING_TIME, FixUtilities.getSendingTime(actualMessage_1));
        assertEquals("Unknown", FixUtilities.getSendingTime(actualMessage_2));
    }

    @Ignore
    @Test
    public void testSenderUtilities() {
        FixMessageTO actualMessage_1 = messageFactory.createFixMessage(SENDING_TIME, SENDER, TARGET);
        FixMessageTO actualMessage_2 = new FixMessageTOBuilder().build();

        assertEquals(SENDER, FixUtilities.getSender(actualMessage_1));
        assertEquals("Unknown", FixUtilities.getSender(actualMessage_2));
    }

    @Ignore
    @Test
    public void testReceiverUtilities() {
        FixMessageTO actualMessage_1 = messageFactory.createFixMessage(SENDING_TIME, SENDER, TARGET);
        FixMessageTO actualMessage_2 = new FixMessageTOBuilder().build();

        assertEquals(TARGET, FixUtilities.getReceiver(actualMessage_1));
        assertEquals("Unknown", FixUtilities.getReceiver(actualMessage_2));
    }

    @Ignore
    @Test
    public void testOrdStatusUtilities() {
        FixMessageTO actualMessage_1 = messageFactory.createFixMessage(
                SENDING_TIME, SENDER, TARGET, new FixPairTO(39, new FixFieldTO(39, "OrdStatus"),
                        new FixValueTO("4", "CANCELED")));
        FixMessageTO actualMessage_2 = new FixMessageTOBuilder().build();

        assertEquals("4", FixUtilities.getOrdStatus(actualMessage_1));
        assertEquals("CANCELED", FixUtilities.getOrdStatusDescription(actualMessage_1));

        assertEquals("", FixUtilities.getOrdStatus(actualMessage_2));
        assertEquals("", FixUtilities.getOrdStatusDescription(actualMessage_2));
    }

}
