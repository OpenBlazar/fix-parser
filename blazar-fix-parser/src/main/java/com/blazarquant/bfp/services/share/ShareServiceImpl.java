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
        String shareMessage = securityUtil.encodeMessage(message);
        shareDAO.saveSharedMessage(shareKey, shareMessage);
        return shareKey;
    }

    @Override
    public String getMessageFromKey(String shareKey) {
        return securityUtil.decodeMessage(shareDAO.findMessageByKey(shareKey));
    }

}
