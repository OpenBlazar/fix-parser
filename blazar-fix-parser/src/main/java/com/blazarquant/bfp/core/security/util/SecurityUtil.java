package com.blazarquant.bfp.core.security.util;

import com.blazarquant.bfp.core.security.exception.DecodingException;

/**
 * @author Wojciech Zankowski
 */
public interface SecurityUtil {

	String hashPassword(char[] password);

	boolean checkPassword(char[] password, String hashedPassword);

	String generateConfirmationKey(long id, String username, String email);

	long decodeConfirmationKey(String confirmationKey) throws DecodingException;

	String generateShareKey();

}
