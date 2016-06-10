package com.blazarquant.bfp.core.security.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.junit.Before;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Wojciech Zankowski
 */
public class BcryptCredentialsMatcherTest {

    private BcryptCredentialsMatcher credentialsMatcher;

    @Before
    public void setUp() {
        credentialsMatcher = new BcryptCredentialsMatcher();
    }

    @Test
    public void testValidPasswordMatching() {
        final String password = "password";
        final String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        UsernamePasswordToken token = mock(UsernamePasswordToken.class);
        AuthenticationInfo info = mock(AuthenticationInfo.class);

        when(token.getPassword()).thenReturn(password.toCharArray());
        when(info.getCredentials()).thenReturn(hashedPassword);

        assertTrue(credentialsMatcher.doCredentialsMatch(token, info));

        when(info.getCredentials()).thenReturn(hashedPassword.toCharArray());
        assertTrue(credentialsMatcher.doCredentialsMatch(token, info));
    }

    @Test
    public void testInvalidPasswordMatching() {
        final String password = "wrongPass";
        final String hashedPassword = BCrypt.hashpw("goodPass", BCrypt.gensalt());

        UsernamePasswordToken token = mock(UsernamePasswordToken.class);
        AuthenticationInfo info = mock(AuthenticationInfo.class);

        when(token.getPassword()).thenReturn(password.toCharArray());
        when(info.getCredentials()).thenReturn(hashedPassword);

        assertFalse(credentialsMatcher.doCredentialsMatch(token, info));

        when(info.getCredentials()).thenReturn(hashedPassword.toCharArray());
        assertFalse(credentialsMatcher.doCredentialsMatch(token, info));
    }

}
