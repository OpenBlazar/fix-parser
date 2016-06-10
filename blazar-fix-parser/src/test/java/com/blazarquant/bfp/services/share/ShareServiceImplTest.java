package com.blazarquant.bfp.services.share;

import com.blazarquant.bfp.common.MockitoUtils;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.database.dao.ShareDAO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Wojciech Zankowski
 */
public class ShareServiceImplTest {

    private SecurityUtil securityUtil;
    private ShareDAO shareDAO;
    private ShareService shareService;

    @Before
    public void setUp() {
        securityUtil = mock(SecurityUtil.class);
        shareDAO = mock(ShareDAO.class);
        shareService = new ShareServiceImpl(shareDAO, securityUtil);
    }

    @Test
    public void testGetMessageFromKey() {
        final String message = "message";
        final String shareKey = "key";

        when(shareDAO.findMessageByKey(shareKey)).thenReturn(message);
        when(securityUtil.decodeMessage(any())).thenAnswer(MockitoUtils.getCallbackAnswer());

        String actualMessage = shareService.getMessageFromKey(shareKey);
        assertEquals(message, actualMessage);
    }



}
