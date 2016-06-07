package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.user.UserID;
import com.blazarquant.bfp.database.providers.MessageSQLProvider;
import com.blazarquant.bfp.fix.data.FixMessage;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface MessageDAO {

    @SelectProvider(type = MessageSQLProvider.class, method = "buildCountUserMessages")
    int countUserMessages(
            @Param("userId") UserID userID);

    @SelectProvider(type = MessageSQLProvider.class, method = "buildFindMessageByUserID")
    List<String> findMessageByUserID(
            @Param("userId") UserID userID,
            @Param("lowerlimit") int lowerLimit,
            @Param("upperlimit") int upperLimit);

    @InsertProvider(type = MessageSQLProvider.class, method = "buildSaveMessage")
    void saveMessage(
            @Param("userId") UserID userID,
            @Param("message") FixMessage message);

    @DeleteProvider(type = MessageSQLProvider.class, method = "buildClearHistory")
    void clearHistory(
            @Param("userId") UserID userID
    );

}
