package pl.zankowski.fixparser.messages.share;

import org.apache.ibatis.jdbc.SQL;

public class ShareSQLProvider {

    public static final String SHARED_MESSAGES = "bfp_sharedmessages";

    public String buildFindMessageByKey() {
        return new SQL() {{
            SELECT("share_message");
            FROM(SHARED_MESSAGES);
            WHERE("share_key = #{shareKey}");
        }}.toString();
    }

    public String buildSaveSharedMessage() {
        return new SQL() {{
            INSERT_INTO(SHARED_MESSAGES);
            VALUES("share_key, share_message", "#{shareKey}, #{shareMessage}");
        }}.toString();
    }

}
