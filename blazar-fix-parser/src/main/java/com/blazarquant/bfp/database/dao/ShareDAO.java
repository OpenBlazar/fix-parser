package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Wojciech Zankowski
 */
public interface ShareDAO {

    String SELECT_MESSAGE = "SELECT share_message FROM " + Tables.SHARED_MESSAGES + " WHERE share_key = #{shareKey}";
    String INSERT_MESSAGE = "INSERT INTO " + Tables.SHARED_MESSAGES + " (share_key, share_message) VALUES (#{shareKey}, #{shareMessage})";

    @Select(SELECT_MESSAGE)
    String findMessageByKey(
            @Param(value = "shareKey") String key);

    @Insert(INSERT_MESSAGE)
    void saveSharedMessage(
            @Param(value = "shareKey") String key,
            @Param(value = "shareMessage") String message);

}
