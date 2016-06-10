package com.blazarquant.bfp.services.share;

import com.blazarquant.bfp.common.MockitoUtils;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.share.exception.ShareException;
import com.blazarquant.bfp.database.dao.ShareDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

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

    @Test
    public void testShareMessageExceptions() {
        try {
            shareService.shareMessage("");
            fail();
        } catch (ShareException e) {
            assertEquals("Failed to share message. Message cannot be empty.", e.getMessage());
        }
        try {
            shareService.shareMessage(new String(new char[8193]).replace('\0', ' '));
            fail();
        } catch (ShareException e) {
            assertTrue(e.getMessage().contains("Message too long."));
        }
    }

    @Test
    public void testShareMessages() throws ShareException {
        final String shareKey = "key";
        final String message = new String(new char[8192]).replace('\0', ' ');

        when(securityUtil.generateShareKey()).thenReturn(shareKey);
        when(securityUtil.encodeMessage(any())).thenAnswer(MockitoUtils.getCallbackAnswer());

        String actualKey = shareService.shareMessage(message);
        assertEquals(shareKey, actualKey);

        final ArgumentCaptor<String> shareKeyCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

        verify(shareDAO, times(1)).saveSharedMessage(shareKeyCaptor.capture(), messageCaptor.capture());
        assertEquals(shareKey, shareKeyCaptor.getValue());
        assertEquals(message, messageCaptor.getValue());
    }


}
