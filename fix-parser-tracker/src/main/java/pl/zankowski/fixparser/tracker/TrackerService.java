package pl.zankowski.fixparser.tracker;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TrackerService {

    List<TrackerData> getTrackerData();

    Map<LocalDate, Integer> getTrackerDailyDataAgg();

    void inputParsed(int messagesNumber);

}
