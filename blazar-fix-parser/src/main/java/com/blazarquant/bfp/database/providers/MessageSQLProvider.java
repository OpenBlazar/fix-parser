package com.blazarquant.bfp.database.providers;

import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Wojciech Zankowski
 */
public class MessageSQLProvider {

    public String buildFindMessageByUserID() {
        return "SELECT message FROM " + Tables.MESSAGES + " WHERE user_id = #{userId.id} LIMIT #{lowerlimit}, #{upperlimit}";
    }

    public String buildCountUserMessages() {
        return new SQL() {{
            SELECT("count(*)");
            FROM(Tables.MESSAGES);
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildSaveMessage() {
        return new SQL() {{
            INSERT_INTO(Tables.MESSAGES);
            VALUES("user_id, message", "#{userId.id}, #{message, typeHandler=com.blazarquant.bfp.database.typehandlers.fix.FixMessageTypeHandler}");
        }}.toString();
    }

    public String buildClearHistory() {
        return new SQL() {{
            DELETE_FROM(Tables.MESSAGES);
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

}
