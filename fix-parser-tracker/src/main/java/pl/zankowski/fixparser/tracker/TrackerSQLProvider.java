package pl.zankowski.fixparser.tracker;

import org.apache.ibatis.jdbc.SQL;

public class TrackerSQLProvider {

    public static final String TRACKER_TABLE = "bfp_tracker";

    public String buildFindTrackerData() {
        return new SQL() {{
            SELECT("*");
            FROM(TRACKER_TABLE);
        }}.toString();
    }

    public String buildSaveInputParse() {
        return new SQL() {{
            INSERT_INTO(TRACKER_TABLE);
            VALUES("tracker_date, tracker_number", "#{parseDate, typeHandler=pl.zankowski.bfp.database.typehandlers.InstantTypeHandler}, #{messageNumber}");
        }}.toString();
    }

}
