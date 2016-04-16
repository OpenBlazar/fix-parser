package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.database.typehandlers.InstantTypeHandler;
import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.Instant;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface TrackerDAO {

    String INSERT_PARSE = "INSERT INTO " + Tables.TRACKER_TABLE + " (tracker_date, tracker_number) VALUES (#{parseDate, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler}, #{messageNumber})";
    String SELECT_TRACKER_DATA = "SELECT * FROM " + Tables.TRACKER_TABLE;

    @Select(SELECT_TRACKER_DATA)
    @ConstructorArgs(value = {
            @Arg(column = "tracker_date", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class),
            @Arg(column = "tracker_number", javaType = int.class, jdbcType = JdbcType.INTEGER)
    })
    List<TrackerData> findTrackerData();

    @Insert(INSERT_PARSE)
    void saveInputParse(
            @Param("parseDate") Instant parseDate,
            @Param("messageNumber") int messageNumber
    );

}
