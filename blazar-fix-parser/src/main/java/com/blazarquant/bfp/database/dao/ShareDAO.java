package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.fix.data.FixMessage;

/**
 * @author Wojciech Zankowski
 */
public interface ShareDAO {

    void saveSharedMessage(String key, String message);

    FixMessage findMessageByKey(String key);

    void deleteSharedMessage(String key);

}
