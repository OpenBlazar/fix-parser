package com.blazarquant.bfp.database.providers;

import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Wojciech Zankowski
 */
public class TrackerSQLProvider {

    public String buildFindTrackerData() {
        return new SQL() {{
            SELECT("*");
            FROM(Tables.TRACKER_TABLE);
        }}.toString();
    }

    public String buildSaveInputParse() {
        return new SQL() {{
            INSERT_INTO(Tables.TRACKER_TABLE);
            VALUES("tracker_date, tracker_number", "#{parseDate, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler}, #{messageNumber}");
        }}.toString();
    }

}
