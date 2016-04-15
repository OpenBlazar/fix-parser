package com.blazarquant.bfp.services.share;

import com.blazarquant.bfp.core.share.exception.ShareException;

/**
 * @author Wojciech Zankowski
 */
public interface ShareService {

    String shareMessage(String message) throws ShareException;

    String getMessageFromKey(String key);

}
