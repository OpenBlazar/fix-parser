package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.database.utils.Tables;
import com.blazarquant.bfp.fix.data.FixMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface MessageDAO {

    String SELECT_MESSAGES_BY_USER_ID = "SELECT message FROM " + Tables.MESSAGES + " WHERE user_id = #{userId.id} LIMIT #{lowerlimit}, #{upperlimit}";
    String SELECT_NUMBER_OF_MSG_FROM_USER_ID = "SELECT count(*) FROM " + Tables.MESSAGES + " WHERE user_id = #{userId.id}";
    String INSERT_MESSAGE = "INSERT INTO " + Tables.MESSAGES + "(user_id, message) VALUES (#{userId.id}, #{message, typeHandler=com.blazarquant.bfp.database.typehandlers.fix.FixMessageTypeHandler})";

    @Select(SELECT_NUMBER_OF_MSG_FROM_USER_ID)
    int countUserMessages(
            @Param("userId") UserID userID);

    @Select(SELECT_MESSAGES_BY_USER_ID)
    List<String> findMessageByUserID(
            @Param("userId") UserID userID,
            @Param("lowerlimit") int lowerLimit,
            @Param("upperlimit") int upperLimit);

    @Insert(INSERT_MESSAGE)
    void saveMessage(
            @Param("userId") UserID userID,
            @Param("message") FixMessage message);

}
