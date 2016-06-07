package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.tracker.TrackerData;
import com.blazarquant.bfp.database.providers.TrackerSQLProvider;
import com.blazarquant.bfp.database.typehandlers.InstantTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.Instant;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface TrackerDAO {

    @SelectProvider(type = TrackerSQLProvider.class, method = "buildFindTrackerData")
    @ConstructorArgs(value = {
            @Arg(column = "tracker_date", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class),
            @Arg(column = "tracker_number", javaType = int.class, jdbcType = JdbcType.INTEGER)
    })
    List<TrackerData> findTrackerData();

    @InsertProvider(type = TrackerSQLProvider.class, method = "buildSaveInputParse")
    void saveInputParse(
            @Param("parseDate") Instant parseDate,
            @Param("messageNumber") int messageNumber
    );

}
