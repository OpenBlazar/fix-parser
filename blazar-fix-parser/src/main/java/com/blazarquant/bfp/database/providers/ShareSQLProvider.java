package com.blazarquant.bfp.database.providers;

import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Wojciech Zankowski
 */
public class ShareSQLProvider {

    public String buildFindMessageByKey() {
        return new SQL() {{
            SELECT("share_message");
            FROM(Tables.SHARED_MESSAGES);
            WHERE("share_key = #{shareKey}");
        }}.toString();
    }

    public String buildSaveSharedMessage() {
        return new SQL() {{
            INSERT_INTO(Tables.SHARED_MESSAGES);
            VALUES("share_key, share_message", "#{shareKey}, #{shareMessage}");
        }}.toString();
    }

}
