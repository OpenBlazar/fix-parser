package com.blazarquant.bfp.core.security.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Wojciech Zankowski
 */
public class SecurityUtilImpl implements SecurityUtil {

	public static final int GENSALT_ROUNDS = 12;

	@Override
	public String hashPassword(char[] password) {
		return BCrypt.hashpw(String.valueOf(password), BCrypt.gensalt(GENSALT_ROUNDS));
	}

	@Override
	public boolean checkPassword(char[] password, String hashedPassword) {
		return BCrypt.checkpw(String.valueOf(password), hashedPassword);
	}

}
