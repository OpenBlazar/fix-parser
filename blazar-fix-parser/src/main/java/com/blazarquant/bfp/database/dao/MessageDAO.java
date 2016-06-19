/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
