package com.blazarquant.bfp.core.security.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class DecodingExceptionTest {

    @Test
    public void testObjectBehaviour() {
        final String MESSAGE = "message";
        DecodingException decodingException = new DecodingException(MESSAGE);
        assertEquals(MESSAGE, decodingException.getMessage());
    }

}
