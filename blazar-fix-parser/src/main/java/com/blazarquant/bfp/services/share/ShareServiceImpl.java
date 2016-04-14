package com.blazarquant.bfp.services.share;

import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.database.dao.ShareDAO;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import com.google.inject.Inject;

/**
 * @author Wojciech Zankowski
 */
public class ShareServiceImpl implements ShareService {

    private final ShareDAO shareDAO;
    private final SecurityUtil securityUtil;
    private final FixMessageConverter fixMessageConverter;

    @Inject
    public ShareServiceImpl(ShareDAO shareDAO, SecurityUtil securityUtil) {
        this.shareDAO = shareDAO;
        this.securityUtil = securityUtil;
        this.fixMessageConverter = new FixMessageConverter();
    }

    @Override
    public String generateKey(FixMessage message) {
        String shareKey = securityUtil.generateShareKey();
        shareDAO.saveSharedMessage(shareKey, fixMessageConverter.convertToString(message));
        return shareKey;
    }

    @Override
    public FixMessage getMessageFromKey(String shareKey) {
        return shareDAO.findMessageByKey(shareKey);
    }

    @Override
    public void removeKey(String shareKey) {
        shareDAO.deleteSharedMessage(shareKey);
    }

}
