package com.blazarquant.bfp.data.tracker;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Wojciech Zankowski
 */
public class TrackerDataTest {

    private final Instant PARSE_DATE = Instant.parse("2016-06-06T16:16:16Z");
    private final int MSG_NUMBER = 14;

    @Test
    public void testNullParameter() {
        try {
            TrackerData trackerData = new TrackerData(null, MSG_NUMBER);
            fail("Test failed. Parse date cannot be null.");
        } catch (NullPointerException e) {
            // success
        }
    }

    @Test
    public void testObjectBehaviour() {
        TrackerData trackerData = new TrackerData(PARSE_DATE, MSG_NUMBER);
        assertEquals(PARSE_DATE, trackerData.getParseDate());
        assertEquals(MSG_NUMBER, trackerData.getMessageNumber());
    }

}
