package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.database.providers.ShareSQLProvider;
import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.annotations.*;

/**
 * @author Wojciech Zankowski
 */
public interface ShareDAO {

    @SelectProvider(type = ShareSQLProvider.class, method = "buildFindMessageByKey")
    String findMessageByKey(
            @Param(value = "shareKey") String key);

    @InsertProvider(type = ShareSQLProvider.class, method = "buildSaveSharedMessage")
    void saveSharedMessage(
            @Param(value = "shareKey") String key,
            @Param(value = "shareMessage") String message);

}
