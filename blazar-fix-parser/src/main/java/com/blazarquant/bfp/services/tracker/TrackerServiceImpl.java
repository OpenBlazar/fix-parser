/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.services.tracker;

import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.database.dao.TrackerDAO;
import com.google.inject.Inject;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
