package com.blazarquant.bfp.services.tracker;

import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.database.dao.TrackerDAO;
import com.google.inject.Inject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public class TrackerServiceImpl implements TrackerService {

    private final TrackerDAO trackerDAO;

    @Inject
    public TrackerServiceImpl(TrackerDAO trackerDAO) {
        this.trackerDAO = trackerDAO;
    }

    @Override
    public Map<Instant, List<Integer>> getDailyData() {
        List<TrackerData> trackerDataList = trackerDAO.findTrackerData();
        Map<Instant, List<Integer>> trackerDataMap = new HashMap<>();
        for (TrackerData trackerData : trackerDataList) {
            List<Integer> dayList = trackerDataMap.get(trackerData.getParseDate());
            if (dayList == null) {
                dayList = new ArrayList<>();
            }
            dayList.add(trackerData.getMessageNumber());
            trackerDataMap.put(trackerData.getParseDate(), dayList);
        }
        return trackerDataMap;
    }

    @Override
    public Map<Instant, Integer> getDailyDataAgg() {
        List<TrackerData> trackerDataList = trackerDAO.findTrackerData();
        Map<Instant, Integer> trackerDataMap = new HashMap<>();
        for (TrackerData trackerData : trackerDataList) {
            Integer dayNumber = trackerDataMap.get(trackerData.getParseDate());
            if (dayNumber == null) {
                dayNumber = 0;
            }
            trackerDataMap.put(trackerData.getParseDate(), dayNumber + trackerData.getMessageNumber());
        }
        return trackerDataMap;
    }

    @Override
    public void inputParsed(int messagesNumber) {
        trackerDAO.saveInputParse(Instant.now(), messagesNumber);
    }
}
