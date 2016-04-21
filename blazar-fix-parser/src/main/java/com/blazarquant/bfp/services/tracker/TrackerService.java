package com.blazarquant.bfp.services.tracker;

import com.blazarquant.bfp.data.tracker.TrackerData;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public interface TrackerService {

    List<TrackerData> getTrackerData();

    Map<LocalDate, Integer> getTrackerDailyDataAgg();

    void inputParsed(int messagesNumber);

}
