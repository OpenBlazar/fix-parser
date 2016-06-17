package com.blazarquant.bfp.core.payments.exception;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Wojciech Zankowski
 */
public class PaymentExceptionTest {

    @Test
    public void testObjectBehaviour() {
        final String MESSAGE = "message";
        PaymentException paymentException = new PaymentException(MESSAGE);
        assertEquals(MESSAGE, paymentException.getMessage());
    }

}
