package pl.zankowski.fixparser.tracker;

import com.google.inject.Inject;
import pl.zankowski.fixparser.core.DateRangeTO;
import pl.zankowski.fixparser.core.ListWrapperTO;
import pl.zankowski.fixparser.tracker.api.TrackerDataTO;
import pl.zankowski.fixparser.tracker.spi.TrackerService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTrackerService implements TrackerService {

    private final TrackerDAO trackerDAO;

    @Inject
    public DefaultTrackerService(TrackerDAO trackerDAO) {
        this.trackerDAO = trackerDAO;
    }

    @Override
    public ListWrapperTO<TrackerDataTO> getTrackerData(final DateRangeTO dateRange) {
        return null;
    }

    @Override
    public void track(final TrackerDataTO trackerData) {
        trackerDAO.saveInputParse(trackerData.getParseDate(), trackerData.getMessageNumber());
    }

    public List<TrackerData> getTrackerData() {
        return trackerDAO.findTrackerData();
    }

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

}
