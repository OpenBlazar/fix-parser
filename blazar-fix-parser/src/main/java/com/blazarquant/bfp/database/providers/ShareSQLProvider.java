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
