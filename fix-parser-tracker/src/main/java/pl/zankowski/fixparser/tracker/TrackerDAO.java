package pl.zankowski.fixparser.tracker;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.InstantTypeHandler;
import org.apache.ibatis.type.JdbcType;
import pl.zankowski.fixparser.core.entity.IDAO;

import java.time.Instant;
import java.util.List;

public interface TrackerDAO extends IDAO {

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
