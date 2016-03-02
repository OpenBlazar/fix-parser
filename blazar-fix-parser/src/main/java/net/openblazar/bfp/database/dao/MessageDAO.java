package net.openblazar.bfp.database.dao;

import net.openblazar.bfp.data.fix.FixMessage;
import net.openblazar.bfp.data.user.UserID;
import net.openblazar.bfp.database.utils.Tables;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface MessageDAO {

    String SELECT_MESSAGES_BY_USER_ID = "SELECT message FROM " + Tables.MESSAGES + " WHERE user_id = #{userId.id} LIMIT #{limit}";
    String INSERT_MESSAGE = "INSERT INTO " + Tables.MESSAGES + "(user_id, message) VALUES (#{userId.id}, #{message, typeHandler=net.openblazar.bfp.database.typehandlers.fix.FixMessageTypeHandler})";

    @Select(SELECT_MESSAGES_BY_USER_ID)
    List<String> findMessageByUserID(
            @Param("userId") UserID userID,
            @Param("limit") int limit);

    @Insert(INSERT_MESSAGE)
    void saveMessage(
            @Param("userId") UserID userID,
            @Param("message") FixMessage message);

}
