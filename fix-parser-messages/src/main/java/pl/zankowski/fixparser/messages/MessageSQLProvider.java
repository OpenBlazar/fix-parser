package pl.zankowski.fixparser.messages;

import org.apache.ibatis.jdbc.SQL;

public class MessageSQLProvider {

    public static final String MESSAGES = "bfp_messages";

    public String buildFindMessageByUserId() {
        return "SELECT message FROM " + MESSAGES + " WHERE user_id = #{userId.id} LIMIT #{lowerlimit}, #{upperlimit}";
    }

    public String buildCountUserMessages() {
        return new SQL() {{
            SELECT("count(*)");
            FROM(MESSAGES);
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildSaveMessage() {
        return new SQL() {{
            INSERT_INTO(MESSAGES);
            VALUES("user_id, message", "#{userId.id}, #{message, typeHandler=pl.zankowski.bfp.database.typehandlers.fix.FixMessageTypeHandler}");
        }}.toString();
    }

    public String buildClearHistory() {
        return new SQL() {{
            DELETE_FROM(MESSAGES);
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

}
