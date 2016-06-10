package com.blazarquant.bfp.services.tracker;

import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.database.dao.TrackerDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class TrackerServiceImplTest {

    private TrackerService trackerService;
    private TrackerDAO trackerDAO;

    @Before
    public void setUp() {
        trackerDAO = mock(TrackerDAO.class);
        trackerService = new TrackerServiceImpl(trackerDAO);
    }

    @Test
    public void testGetTrackerData() {
        List<TrackerData> trackerDataList = Arrays.asList(
                new TrackerData(Instant.now(), 9),
                new TrackerData(Instant.now(), 11),
                new TrackerData(Instant.now(), 12)
        );
        when(trackerDAO.findTrackerData()).thenReturn(trackerDataList);

        List<TrackerData> actualTrackerDataList = trackerService.getTrackerData();
        assertEquals(trackerDataList, actualTrackerDataList);
    }

    @Test
    public void testGetTrackerDataDailyAgg() {
        List<TrackerData> trackerDataList = Arrays.asList(
                new TrackerData(Instant.now().minus(Duration.ofDays(2)), 9),
                new TrackerData(Instant.now(), 11),
                new TrackerData(Instant.now(), 12)
        );
        when(trackerDAO.findTrackerData()).thenReturn(trackerDataList);

        Map<LocalDate, Integer> expectedResult = new HashMap<>();
        expectedResult.put(LocalDate.now().minusDays(2), 9);
        expectedResult.put(LocalDate.now(), 23);

        Map<LocalDate, Integer> result = trackerService.getTrackerDailyDataAgg();
        assertEquals(2, result.size());
        assertEquals(expectedResult, result);
    }

    @Test
    public void testInputParsed() {
        final Integer messageNumber = 21;

        final ArgumentCaptor<Instant> dateCaptor = ArgumentCaptor.forClass(Instant.class);
        final ArgumentCaptor<Integer> messageNumberCaptor = ArgumentCaptor.forClass(Integer.class);

        trackerService.inputParsed(messageNumber);

        verify(trackerDAO).saveInputParse(dateCaptor.capture(), messageNumberCaptor.capture());
        assertEquals(messageNumber, messageNumberCaptor.getValue());
        assertEquals(LocalDate.now(), dateCaptor.getValue().atZone(ZoneId.systemDefault()).toLocalDate());
    }

}
