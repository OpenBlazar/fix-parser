package com.blazarquant.bfp.services.user;

import com.blazarquant.bfp.common.TestObjectFactory;
import com.blazarquant.bfp.core.security.exception.DecodingException;
import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.user.UserSettingsCache;
import com.blazarquant.bfp.data.user.*;
import com.blazarquant.bfp.database.dao.UserDAO;
import com.blazarquant.bfp.services.mail.MailService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class UserServiceImplTest {

    private UserService userService;
    private UserDAO userDAO;
    private MailService mailService;
    private SecurityUtil securityUtil;

    @Before
    public void setUp() {
        userDAO = mock(UserDAO.class);
        mailService = mock(MailService.class);
        securityUtil = mock(SecurityUtil.class);
        userService = new UserServiceImpl(userDAO, securityUtil, mailService);
    }

    @Test
    public void testGetUserDetails() {
        final String userName = "test";
        final UserDetails userDetails = TestObjectFactory.createUserDetails(new UserID(9), userName);
        when(userDAO.findUserByLogin(any())).thenReturn(userDetails);

        UserDetails actualUserDetails = userService.getUserDetails(userName);
        assertEquals(userDetails, actualUserDetails);
    }

    @Test
    public void testGetUserDetailsByMail() {
        final String userMail = "test2@test.com";
        final UserDetails userDetails = TestObjectFactory.createUserDetails(new UserID(0), "test", userMail);
        when(userDAO.findUserByMail(userMail)).thenReturn(userDetails);

        UserDetails actualUserDetails = userService.getUserDetailsByMail(userMail);
        assertEquals(userDetails, actualUserDetails);
    }

    @Test
    public void testGetUsers() {
        List<UserDetails> userDetailsList = Arrays.asList(
                TestObjectFactory.createUserDetails(new UserID(0), "test"),
                TestObjectFactory.createUserDetails(new UserID(1), "test1"),
                TestObjectFactory.createUserDetails(new UserID(2), "test2")
        );
        when(userDAO.findAllUsers()).thenReturn(userDetailsList);

        List<UserDetails> actualUserDetailsList = userService.getUsers();
        assertEquals(userDetailsList, actualUserDetailsList);
    }

    @Test
    public void testGetUserRoles() {
        final UserID userID = new UserID(0);
        List<Role> roles = Arrays.asList(
                Role.USER_ROLE,
                Role.ADMIN_ROLE
        );
        when(userDAO.findUserRoles(userID)).thenReturn(roles);

        List<Role> actualRoles = userService.getUserRoles(userID);
        assertEquals(roles, actualRoles);
    }

    @Test
    public void testAddUserPermission() {
        final UserID userID = new UserID(0);
        final Permission permission_1 = Permission.PRO;

        ArgumentCaptor<UserID> userIDCaptor = ArgumentCaptor.forClass(UserID.class);
        ArgumentCaptor<String> permissionCaptor = ArgumentCaptor.forClass(String.class);

        userService.addUserPermission(userID, permission_1);
        verify(userDAO, times(1)).saveUserPermission(userIDCaptor.capture(), permissionCaptor.capture());
        assertEquals(userID, userIDCaptor.getValue());
        assertEquals(permission_1.name(), permissionCaptor.getValue());

        final Permission permission_2 = Permission.ENTERPRISE;

        userService.addUserPermission(userID, permission_2);
        verify(userDAO, times(2)).saveUserPermission(userIDCaptor.capture(), permissionCaptor.capture());
        assertEquals(userID, userIDCaptor.getValue());
        assertEquals(permission_2.name(), permissionCaptor.getValue());
    }

    @Test
    public void testIsUserNameExists() {
        final String userNameTrue = "testTrue";
        final String userNameFalse = "testFalse";
        when(userDAO.isUserNameExists(userNameFalse)).thenReturn(0);
        when(userDAO.isUserNameExists(userNameTrue)).thenReturn(1);

        assertTrue(userService.isUserNameExists(userNameTrue));
        assertFalse(userService.isUserNameExists(userNameFalse));
    }

    @Test
    public void testIsUserMailExists() {
        final String userMailTrue = "testTrue";
        final String userMailFalse = "testFalse";
        when(userDAO.isUserMailExists(userMailFalse)).thenReturn(0);
        when(userDAO.isUserMailExists(userMailTrue)).thenReturn(1);

        assertTrue(userService.isUserMailExists(userMailTrue));
        assertFalse(userService.isUserMailExists(userMailFalse));
    }

    @Test
    public void testIsUserActive() {
        final String userNameTrue = "testTrue";
        final String userNameFalse = "testFalse";
        when(userDAO.isUserActive(userNameFalse)).thenReturn(0);
        when(userDAO.isUserActive(userNameTrue)).thenReturn(1);

        assertTrue(userService.isUserActive(userNameTrue));
        assertFalse(userService.isUserActive(userNameFalse));
    }

    @Test
    public void testRegisterUser() {
        final String userName = "test";
        final String userMail = "test@test.com";
        final String pass = "pass";
        final char[] password = pass.toCharArray();
        final String confirmationKey = "key";
        final UserID userID = new UserID(0);

        when(securityUtil.hashPassword(password)).thenReturn(pass);
        when(securityUtil.generateConfirmationKey(userID.getId(), userName, userMail)).thenReturn(confirmationKey);
        when(userDAO.findUserIDByLogin(userName)).thenReturn(userID.getId());

        assertTrue(userService.registerUser(userName, userMail, password));

        final ArgumentCaptor<String> userNameCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> userMailCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<UserState> userStateCaptor = ArgumentCaptor.forClass(UserState.class);
        final ArgumentCaptor<Instant> registerDateCaptor = ArgumentCaptor.forClass(Instant.class);
        final ArgumentCaptor<Instant> lastLoginCaptor = ArgumentCaptor.forClass(Instant.class);

        verify(userDAO, times(1)).saveUser(userNameCaptor.capture(), userMailCaptor.capture(), passwordCaptor.capture(),
                userStateCaptor.capture(), registerDateCaptor.capture(), lastLoginCaptor.capture());
        assertEquals(userName, userNameCaptor.getValue());
        assertEquals(userMail, userMailCaptor.getValue());
        assertEquals(pass, passwordCaptor.getValue());
        assertEquals(UserState.INACTIVE, userStateCaptor.getValue());
        assertEquals(LocalDate.now(), registerDateCaptor.getValue().atZone(ZoneId.systemDefault()).toLocalDate());
        assertEquals(LocalDate.now(), lastLoginCaptor.getValue().atZone(ZoneId.systemDefault()).toLocalDate());

        final ArgumentCaptor<UserID> userIDCaptor = ArgumentCaptor.forClass(UserID.class);
        final ArgumentCaptor<Role> roleCaptor = ArgumentCaptor.forClass(Role.class);

        verify(userDAO, times(1)).saveUserRole(userIDCaptor.capture(), roleCaptor.capture());
        assertEquals(userID, userIDCaptor.getValue());
        assertEquals(Role.USER_ROLE, roleCaptor.getValue());

        final ArgumentCaptor<String> permissionCaptor = ArgumentCaptor.forClass(String.class);

        verify(userDAO, times(1)).saveUserPermission(userIDCaptor.capture(), permissionCaptor.capture());
        assertEquals(userID, userIDCaptor.getValue());
        assertEquals(Permission.BASIC.name(), permissionCaptor.getValue());

        final ArgumentCaptor<Long> longUserIDCaptor = ArgumentCaptor.forClass(Long.class);
        final ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);

        verify(userDAO, times(1)).updateConfirmationKey(longUserIDCaptor.capture(), keyCaptor.capture());
        assertEquals(Long.valueOf(userID.getId()), longUserIDCaptor.getValue());
        assertEquals(confirmationKey, keyCaptor.getValue());

        verify(mailService, times(1)).sendConfirmationLink(keyCaptor.capture(), userMailCaptor.capture());
        assertEquals(confirmationKey, keyCaptor.getValue());
        assertEquals(userMail, userMailCaptor.getValue());
    }

    @Test
    public void testConfirmUser() throws DecodingException {
        final String confirmationKey = "key";
        final UserID userID = new UserID(0);
        when(securityUtil.decodeConfirmationKey(confirmationKey)).thenReturn(userID.getId());
        when(userDAO.findConfirmationKeyFromUser(userID.getId())).thenReturn(confirmationKey);

        userService.confirmUser(confirmationKey);

        final ArgumentCaptor<Long> userIDCaptor = ArgumentCaptor.forClass(Long.class);
        final ArgumentCaptor<UserState> userStateCaptor = ArgumentCaptor.forClass(UserState.class);
        verify(userDAO).updateUserStatus(userIDCaptor.capture(), userStateCaptor.capture());

        assertEquals(Long.valueOf(userID.getId()), userIDCaptor.getValue());
        assertEquals(UserState.ACTIVE, userStateCaptor.getValue());
    }

    @Test
    public void testGetUserSettingsCache() {
        UserSettingsCache userSettingsCache = userService.getUserSettingsCache();
        assertNotNull(userSettingsCache);
    }

}
