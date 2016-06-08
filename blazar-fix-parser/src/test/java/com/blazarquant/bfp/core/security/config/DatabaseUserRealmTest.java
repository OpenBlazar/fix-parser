package com.blazarquant.bfp.core.security.config;

import com.blazarquant.bfp.data.user.*;
import com.blazarquant.bfp.database.dao.UserDAO;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Wojciech Zankowski
 */
public class DatabaseUserRealmTest {

    private UserDAO userDAO;
    private BCryptCredentialsMatcher credentialsMatcher;
    private DatabaseUserRealm databaseUserRealm;

    @Before
    public void setUp() {
        userDAO = mock(UserDAO.class);
        credentialsMatcher = new BCryptCredentialsMatcher();
        databaseUserRealm = new DatabaseUserRealm(userDAO, credentialsMatcher);
    }

    @Test
    public void testDoGetAuthenticationInfo() {
        final String username = "test";
        final UserDetails userDetails = new UserDetails(new UserID(0), username,
                "test@test.com", "pass", UserState.ACTIVE, Instant.now(), Instant.now());

        UsernamePasswordToken token = mock(UsernamePasswordToken.class);
        when(token.getUsername()).thenReturn(username);
        when(userDAO.findUserByLogin(username)).thenReturn(userDetails);

        SimpleAuthenticationInfo authenticationInfo = (SimpleAuthenticationInfo) databaseUserRealm.doGetAuthenticationInfo(token);
        assertEquals(userDetails, (UserDetails) authenticationInfo.getPrincipals().getPrimaryPrincipal());
        assertEquals(userDetails.getPassword(), authenticationInfo.getCredentials());
    }

    @Test
    public void testDoGetAuthorizationInfo() {
        final String username = "test";
        final UserDetails userDetails = new UserDetails(new UserID(0), username,
                "test@test.com", "pass", UserState.ACTIVE, Instant.now(), Instant.now());

        final List<String> permissions = Arrays.asList(Permission.BASIC.name(), Permission.PRO.name());
        final List<Role> roles = Arrays.asList(Role.USER_ROLE, Role.ADMIN_ROLE);

        PrincipalCollection principalCollection = mock(PrincipalCollection.class);
        when(principalCollection.fromRealm(any())).thenReturn((Collection) Arrays.asList(userDetails));
        when(userDAO.findUserPermissions(userDetails.getUserID())).thenReturn(permissions);
        when(userDAO.findUserRoles(userDetails.getUserID())).thenReturn(roles);

        AuthorizationInfo authorizationInfo = databaseUserRealm.doGetAuthorizationInfo(principalCollection);

        assertEquals(permissions.size(), authorizationInfo.getStringPermissions().size());
        for (String permission : permissions) {
            assertTrue(authorizationInfo.getStringPermissions().contains(permission));
        }
        assertEquals(roles.size(), authorizationInfo.getRoles().size());
        for (Role role : roles) {
            assertTrue(authorizationInfo.getRoles().contains(role.getName()));
        }
    }


}
