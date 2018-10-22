package pl.zankowski.fixparser.tracker;

import com.google.inject.Inject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackerServiceImpl implements TrackerService {

    private final TrackerDAO trackerDAO;

    @Inject
    public TrackerServiceImpl(TrackerDAO trackerDAO) {
        this.trackerDAO = trackerDAO;
    }

    @Override
    public List<TrackerData> getTrackerData() {
        return trackerDAO.findTrackerData();
    }

    @Override
    public Map<LocalDate, Integer> getTrackerDailyDataAgg() {
        List<TrackerData> trackerDataList = trackerDAO.findTrackerData();
        Map<LocalDate, Integer> trackerDataMap = new HashMap<>();
        for (TrackerData trackerData : trackerDataList) {
            LocalDate date = LocalDateTime.ofInstant(trackerData.getParseDate(), ZoneId.systemDefault()).toLocalDate();
            Integer dayNumber = trackerDataMap.get(date);
            if (dayNumber == null) {
                dayNumber = 0;
            }
            trackerDataMap.put(date, dayNumber + trackerData.getMessageNumber());
        }
        return trackerDataMap;
    }

    @Override
    public void inputParsed(int messagesNumber) {
        trackerDAO.saveInputParse(Instant.now(), messagesNumber);
    }
}