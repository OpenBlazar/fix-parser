package com.blazarquant.bfp.core.share.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class ShareExceptionTest {

    @Test
    public void testObjectBehaviour() {
        final String MESSAGE = "message";
        ShareException shareException = new ShareException(MESSAGE);
        assertEquals(MESSAGE, shareException.getMessage());
    }

}
