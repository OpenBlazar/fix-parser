package com.blazarquant.bfp.core.security.util;

/**
 * @author Wojciech Zankowski
 */
public interface SecurityUtil {

	String hashPassword(char[] password);

	boolean checkPassword(char[] password, String hashedPassword);

}
