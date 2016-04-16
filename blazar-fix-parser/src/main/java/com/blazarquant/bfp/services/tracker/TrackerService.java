package com.blazarquant.bfp.services.tracker;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public interface TrackerService {

    Map<Instant, List<Integer>> getDailyData();

    Map<Instant, Integer> getDailyDataAgg();

    void inputParsed(int messagesNumber);

}
