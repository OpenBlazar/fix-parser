package net.openblazar.bfp.core.security.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Wojciech Zankowski
 */
public class BcryptCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String storedBcryptPassword;
        if (info.getCredentials() instanceof char[]) {
            storedBcryptPassword = new String((char[]) info.getCredentials());
        } else {
            storedBcryptPassword = info.getCredentials().toString();
        }
        final String assertedPlaintextPassword = new String(upToken.getPassword());
        return BCrypt.checkpw(assertedPlaintextPassword, storedBcryptPassword);
    }

}
