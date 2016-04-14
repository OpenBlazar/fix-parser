package com.blazarquant.bfp.services.share;

import com.blazarquant.bfp.fix.data.FixMessage;

/**
 * @author Wojciech Zankowski
 */
public interface ShareService {

    String generateKey(FixMessage message);

    FixMessage getMessageFromKey(String key);

    void removeKey(String shareKey);

}
