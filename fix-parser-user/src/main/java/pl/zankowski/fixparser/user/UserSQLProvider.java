package pl.zankowski.fixparser.user;

import org.apache.ibatis.jdbc.SQL;

public class UserSQLProvider {

    public static final String USERS_TABLE = "bfp_users";
    public static final String ROLES_TABLE = "bfp_roles";
    public static final String USER_ROLES_TABLE = "bfp_userroles";
    public static final String USER_PERMISSIONS_TABLE = "bfp_userpermissions";
    public static final String USER_PARAMETERS = "bfp_userparameters";
    public static final String PERMISSIONS_TABLE = "bfp_permissions";

    public String buildFindAllUsers() {
        return new SQL() {{
            SELECT("*");
            FROM(USERS_TABLE);
        }}.toString();
    }

    public String buildFindUserByLogin() {
        return new SQL() {{
            SELECT("*");
            FROM(USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildFindUserByMail() {
        return new SQL() {{
            SELECT("*");
            FROM(USERS_TABLE);
            WHERE("user_email = #{userMail}");
        }}.toString();
    }

    public String buildFindUserIDByLogin() {
        return new SQL() {{
            SELECT("ID");
            FROM(USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildFindUserById() {
        return new SQL() {{
            SELECT("*");
            FROM(USERS_TABLE);
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildFindUserRoles() {
        return new SQL() {{
            SELECT("role_name");
            FROM(USER_ROLES_TABLE);
            JOIN(ROLES_TABLE + " ON "
                    + USER_ROLES_TABLE + ".role_id = " + ROLES_TABLE + ".ID");
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildFindUserPermissions() {
        return new SQL() {{
            SELECT("permission_name");
            FROM(USER_PERMISSIONS_TABLE);
            JOIN(PERMISSIONS_TABLE + " ON "
                    + USER_PERMISSIONS_TABLE + ".permission_id = " + PERMISSIONS_TABLE + ".id");
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildIsUserNameExists() {
        return new SQL() {{
            SELECT("count(1)");
            FROM(USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildIsUserMailExists() {
        return new SQL() {{
            SELECT("count(1)");
            FROM(USERS_TABLE);
            WHERE("user_email = #{userMail}");
        }}.toString();
    }

    public String buildIsUserActive() {
        return new SQL() {{
            SELECT("user_status");
            FROM(USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildFindConfirmationKeyFromUser() {
        return new SQL() {{
            SELECT("user_confirmationkey");
            FROM(USERS_TABLE);
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildSaveUser() {
        return new SQL() {{
            INSERT_INTO(USERS_TABLE);
            VALUES(
                    "user_login, user_pass, user_email, user_status, user_registerdate, user_lastlogin",
                    "#{userName}, #{hashedPassword}, #{userMail}, #{isActive, typeHandler=pl.zankowski.fixparser.user.handler.ActiveUserTypeHandler}, " +
                            "#{registrationDate, typeHandler=pl.zankowski.bfp.database.typehandlers.InstantTypeHandler}, " +
                            "#{lastLogin, typeHandler=pl.zankowski.bfp.database.typehandlers.InstantTypeHandler}"
            );
        }}.toString();
    }

    public String buildUpdateConfirmationKey() {
        return new SQL() {{
            UPDATE(USERS_TABLE);
            SET("user_confirmationkey = #{confirmationKey}");
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildUpdateUserLastLogin() {
        return new SQL() {{
            UPDATE(USERS_TABLE);
            SET("user_lastlogin = #{lastLogin, typeHandler=pl.zankowski.bfp.database.typehandlers.InstantTypeHandler}");
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildUpdateUserStatus() {
        return new SQL() {{
            UPDATE(USERS_TABLE);
            SET("user_status=#{userStatus, typeHandler=pl.zankowski.fixparser.user.handler.ActiveUserTypeHandler}");
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildFindParameters() {
        return new SQL() {{
            SELECT("user_setting, setting_value");
            FROM(USER_PARAMETERS);
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildSaveUserRole() {
        return "INSERT INTO " + USER_ROLES_TABLE + " (user_id, role_id) SELECT #{userId.id}, ID FROM " + ROLES_TABLE + " WHERE role_name  = #{role.name}";
    }

    public String buildSaveUserPermission() {
        return "INSERT INTO " + USER_PERMISSIONS_TABLE + " (permission_id, user_id) SELECT id, #{userId.id} FROM "
                + PERMISSIONS_TABLE + " WHERE permission_name = #{permission}";
    }

    public String buildSaveParameter() {
        return "INSERT INTO " + USER_PARAMETERS + " (user_id, user_setting, setting_value) VALUES " +
                "(#{userId.id}, #{userSetting, typeHandler=pl.zankowski.fixparser.user.handler.UserSettingTypeHandler}, #{value}) " +
                "ON DUPLICATE KEY UPDATE user_id=VALUES(user_id), user_setting=VALUES(user_setting), setting_value=VALUES(setting_value)";
    }

}
