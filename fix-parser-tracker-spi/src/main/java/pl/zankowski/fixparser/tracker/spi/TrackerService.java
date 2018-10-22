package pl.zankowski.fixparser.tracker.spi;

import pl.zankowski.fixparser.core.DateRangeTO;
import pl.zankowski.fixparser.core.ListWrapperTO;
import pl.zankowski.fixparser.tracker.api.TrackerDataTO;

public interface TrackerService {

    ListWrapperTO<TrackerDataTO> getTrackerData(DateRangeTO dateRange);

    void track(TrackerDataTO trackerData);

}
