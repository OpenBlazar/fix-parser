package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.user.*;
import com.blazarquant.bfp.database.util.DatabaseTestBase;
import org.junit.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Wojciech Zankowski
 */
public class UserDAOTest extends DatabaseTestBase {

    @Test
    public void testSelectAll() {
        List<UserDetails> users = userDAO.findAllUsers();

        assertEquals(2, users.size());

        UserDetails userDetails = users.get(1);
        assertZanoUser(userDetails);
    }

    @Test
    public void testSelectUserByLogin() {
        UserDetails userDetails = userDAO.findUserByLogin("Zano");
        assertNotNull(userDetails);
        assertZanoUser(userDetails);
    }

    @Test
    public void testSelectUserByMail() {
        UserDetails userDetails = userDAO.findUserByMail("wojciech@zankowski.pl");
        assertNotNull(userDetails);
        assertZanoUser(userDetails);
    }

    @Test
    public void testSelectUserIDByLogin() {
        UserID userID = userDAO.findUserIDByLogin("Zano");
        assertNotNull(userID);
        assertEquals(9L, userID.getId());
    }

    @Test
    public void testSelectUserById() {
        UserDetails userDetails = userDAO.findUserById(new UserID(9));
        assertNotNull(userDetails);
        assertZanoUser(userDetails);
    }

    @Test
    public void testSelectUserRoles() {
        List<Role> roles = userDAO.findUserRoles(new UserID(9));
        assertEquals(1, roles.size());
        List<Role> expectedRoles = Arrays.asList(Role.USER_ROLE);
        assertEquals(expectedRoles, roles);
    }

    @Test
    public void testInsertUserRole() {
        userDAO.saveUserRole(new UserID(9), Role.ADMIN_ROLE);

        List<Role> roles = userDAO.findUserRoles(new UserID(9));
        assertEquals(2, roles.size());
        List<Role> expectedRoles = Arrays.asList(Role.USER_ROLE, Role.ADMIN_ROLE);
        assertEquals(expectedRoles, roles);
    }

    @Test
    public void testSelectUserPermissions() {
        List<String> permissions = userDAO.findUserPermissions(new UserID(9));
        assertEquals(2, permissions.size());
        List<String> expectedPermissions = Arrays.asList(Permission.BASIC.name(), Permission.PRO.name());
        assertEquals(expectedPermissions, permissions);
    }

    @Test
    public void testInsertUserPermission() {
        userDAO.saveUserPermission(new UserID(9), Permission.ENTERPRISE.name());

        List<String> permissions = userDAO.findUserPermissions(new UserID(9));
        assertEquals(3, permissions.size());
        List<String> expectedPermissions = Arrays.asList(Permission.BASIC.name(), Permission.PRO.name(), Permission.ENTERPRISE.name());
        assertEquals(expectedPermissions, permissions);
    }

    @Test
    public void testCheckIfUserExists() {
        Integer exists = userDAO.isUserNameExists("Zano");
        assertEquals(Integer.valueOf(1), exists);

        exists = userDAO.isUserNameExists("Zano2");
        assertEquals(Integer.valueOf(0), exists);
    }

    @Test
    public void testCheckIfUserMailExists() {
        Integer exists = userDAO.isUserMailExists("wojciech@zankowski.pl");
        assertEquals(Integer.valueOf(1), exists);

        exists = userDAO.isUserMailExists("info@zankowski.pl");
        assertEquals(Integer.valueOf(0), exists);
    }

    @Test
    public void testCheckIfUserActive() {
        int active = userDAO.isUserActive("Zano");
        assertEquals(UserState.ACTIVE.getState(), active);
    }

    @Test
    public void testSelectUserConfirmationKey() {
        final UserID userID = new UserID(9);
        String confirmationKey = userDAO.findConfirmationKeyFromUser(userID);
        assertEquals("OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA==", confirmationKey);
    }

    @Test
    public void testInsertUserRegister() {
        List<UserDetails> userDetails = userDAO.findAllUsers();
        assertEquals(2, userDetails.size());

        userDAO.saveUser("testName", "testMail", "testPass", UserState.ACTIVE,
                Instant.ofEpochMilli(1460841158000L), Instant.ofEpochMilli(1460841158001L));
        userDetails = userDAO.findAllUsers();
        assertEquals(3, userDetails.size());

        UserDetails testUser = userDetails.get(2);
        assertEquals(new UserID(10), testUser.getUserID());
        assertEquals("testName", testUser.getUserName());
        assertEquals("testPass", testUser.getPassword());
        assertEquals("testMail", testUser.getUserMail());
        assertEquals(UserState.ACTIVE, testUser.getUserState());
        assertEquals(Instant.ofEpochMilli(1460841158000L), testUser.getRegistrationDate());
        assertEquals(Instant.ofEpochMilli(1460841158001L), testUser.getLastLogin());
    }

    @Test
    public void testUpdateUserConfirmationKey() {
        final UserID userID = new UserID(9);

        String confirmationKey = userDAO.findConfirmationKeyFromUser(userID);
        assertEquals("OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA==", confirmationKey);

        String newConfirmationKey = "updateTest";
        userDAO.updateConfirmationKey(userID, newConfirmationKey);
        confirmationKey = userDAO.findConfirmationKeyFromUser(userID);
        assertEquals(newConfirmationKey, confirmationKey);
    }

    @Test
    public void testUpdateLastLogin() {
        final UserID userID = new UserID(9);
        final Instant newLastLogin = Instant.ofEpochMilli(1460958358001L);

        UserDetails userDetails = userDAO.findUserById(userID);
        assertEquals(Instant.ofEpochMilli(1460848358001L), userDetails.getLastLogin());

        userDAO.updateLastLogin(userID, newLastLogin);
        userDetails = userDAO.findUserById(userID);
        assertEquals(newLastLogin, userDetails.getLastLogin());
    }

    @Test
    public void testUpdateUserStatus() {
        final UserID userID = new UserID(9);

        int active = userDAO.isUserActive("Zano");
        assertEquals(UserState.ACTIVE.getState(), active);

        userDAO.updateUserStatus(userID, UserState.INACTIVE);
        active = userDAO.isUserActive("Zano");
        assertEquals(UserState.INACTIVE.getState(), active);
    }

    @Test
    public void testSelectParameters() {
        List<UserSettingHolder> settingHolders = userDAO.findParameters(new UserID(9));
        assertEquals(1, settingHolders.size());

        UserSettingHolder holder = settingHolders.get(0);
        assertEquals(UserSetting.DEFAULT_PROVIDER, holder.getUserSetting());
        assertEquals("QF#FIX42", holder.getSettingValue());
    }

    private void assertZanoUser(UserDetails userDetails) {
        assertEquals(new UserID(9), userDetails.getUserID());
        assertEquals("Zano", userDetails.getUserName());
        assertEquals("$2a$12$b9AGT0nw80sLSj/pk3gYkuLczCmuvZu1ZE9IBOuMJy14IjCIItziy", userDetails.getPassword());
        assertEquals("wojciech@zankowski.pl", userDetails.getUserMail());
        assertEquals(UserState.ACTIVE, userDetails.getUserState());
        assertEquals(Instant.ofEpochMilli(1460848358000L), userDetails.getRegistrationDate());
        assertEquals(Instant.ofEpochMilli(1460848358001L), userDetails.getLastLogin());
    }

}
