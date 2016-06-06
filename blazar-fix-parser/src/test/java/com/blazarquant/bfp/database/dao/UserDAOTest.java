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
        Long userID = userDAO.findUserIDByLogin("Zano");
        assertNotNull(userID);
        assertEquals(Long.valueOf(9L), userID);
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
        String confirmationKey = userDAO.findConfirmationKeyFromUser(9);
        assertEquals("OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA==", confirmationKey);
    }

    @Test
    public void testInsertUserRegister() {
        List<UserDetails> userDetails = userDAO.findAllUsers();
        assertEquals(2, userDetails.size());

        userDAO.saveUser("testName", "testMail", "testPass", UserState.ACTIVE, Instant.parse("2016-04-16T21:12:38Z"), Instant.parse("2016-04-16T21:12:39Z"));
        userDetails = userDAO.findAllUsers();
        assertEquals(3, userDetails.size());

        UserDetails testUser = userDetails.get(2);
        assertEquals(new UserID(10), testUser.getUserID());
        assertEquals("testName", testUser.getUserName());
        assertEquals("testPass", testUser.getPassword());
        assertEquals("testMail", testUser.getUserMail());
        assertEquals(UserState.ACTIVE, testUser.getUserState());
        assertEquals(Instant.parse("2016-04-16T21:12:38Z"), testUser.getRegistrationDate());
        assertEquals(Instant.parse("2016-04-16T21:12:39Z"), testUser.getLastLogin());
    }

    @Test
    public void testUpdateUserConfirmationKey() {
        String confirmationKey = userDAO.findConfirmationKeyFromUser(9);
        assertEquals("OTtaYW5vO3dvamNpZWNoQHphbmtvd3NraS5wbA==", confirmationKey);

        String newConfirmationKey = "updateTest";
        userDAO.updateConfirmationKey(9, newConfirmationKey);
        confirmationKey = userDAO.findConfirmationKeyFromUser(9);
        assertEquals(newConfirmationKey, confirmationKey);
    }

    @Test
    public void testUpdateUserStatus() {
        int active = userDAO.isUserActive("Zano");
        assertEquals(UserState.ACTIVE.getState(), active);

        userDAO.updateUserStatus(9, UserState.INACTIVE);
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
        assertEquals(Instant.parse("2016-04-16T21:12:38Z"), userDetails.getRegistrationDate());
        assertEquals(Instant.parse("2016-04-16T21:12:38Z"), userDetails.getLastLogin());
    }

}
