package com.blazarquant.bfp.services.share;

import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.share.exception.ShareException;
import com.blazarquant.bfp.database.dao.ShareDAO;
import com.google.inject.Inject;

/**
 * @author Wojciech Zankowski
 */
public class ShareServiceImpl implements ShareService {

    public static final int CHARACTER_LIMIT = 8192;

    private final ShareDAO shareDAO;
    private final SecurityUtil securityUtil;

    @Inject
    public ShareServiceImpl(ShareDAO shareDAO, SecurityUtil securityUtil) {
        this.shareDAO = shareDAO;
        this.securityUtil = securityUtil;
    }

    @Override
    public String shareMessage(String message) throws ShareException {
        if (message.isEmpty()) {
            throw new ShareException("Failed to share message. Message cannot be empty.");
        }
        if (message.length() > CHARACTER_LIMIT) {
            throw new ShareException("Message too long. Message limit is " + CHARACTER_LIMIT + " characters.");
        }
        String shareKey = securityUtil.generateShareKey();
        shareDAO.saveSharedMessage(shareKey, message);
        return shareKey;
    }

    @Override
    public String getMessageFromKey(String shareKey) {
        return shareDAO.findMessageByKey(shareKey);
    }

}
