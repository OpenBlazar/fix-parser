package net.openblazar.bfp.core.user;

/**
 * @author Wojciech Zankowski
 */
public interface SecurityUtil {

	String hashPassword(char[] password);

	boolean checkPassword(char[] password, String hashedPassword);

}
