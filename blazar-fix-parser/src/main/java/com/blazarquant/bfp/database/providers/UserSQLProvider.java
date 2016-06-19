/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.database.providers;

import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Wojciech Zankowski
 */
public class UserSQLProvider {

    public String buildFindAllUsers() {
        return new SQL() {{
            SELECT("*");
            FROM(Tables.USERS_TABLE);
        }}.toString();
    }

    public String buildFindUserByLogin() {
        return new SQL() {{
            SELECT("*");
            FROM(Tables.USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildFindUserByMail() {
        return new SQL() {{
            SELECT("*");
            FROM(Tables.USERS_TABLE);
            WHERE("user_email = #{userMail}");
        }}.toString();
    }

    public String buildFindUserIDByLogin() {
        return new SQL() {{
            SELECT("ID");
            FROM(Tables.USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildFindUserById() {
        return new SQL() {{
            SELECT("*");
            FROM(Tables.USERS_TABLE);
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildFindUserRoles() {
        return new SQL() {{
            SELECT("role_name");
            FROM(Tables.USER_ROLES_TABLE);
            JOIN(Tables.ROLES_TABLE + " ON "
                    + Tables.USER_ROLES_TABLE + ".role_id = " + Tables.ROLES_TABLE + ".ID");
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildFindUserPermissions() {
        return new SQL() {{
            SELECT("permission_name");
            FROM(Tables.USER_PERMISSIONS_TABLE);
            JOIN(Tables.PERMISSIONS_TABLE + " ON "
                    + Tables.USER_PERMISSIONS_TABLE + ".permission_id = " + Tables.PERMISSIONS_TABLE + ".id");
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildIsUserNameExists() {
        return new SQL() {{
            SELECT("count(1)");
            FROM(Tables.USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildIsUserMailExists() {
        return new SQL() {{
            SELECT("count(1)");
            FROM(Tables.USERS_TABLE);
            WHERE("user_email = #{userMail}");
        }}.toString();
    }

    public String buildIsUserActive() {
        return new SQL() {{
            SELECT("user_status");
            FROM(Tables.USERS_TABLE);
            WHERE("user_login = #{userName}");
        }}.toString();
    }

    public String buildFindConfirmationKeyFromUser() {
        return new SQL() {{
            SELECT("user_confirmationkey");
            FROM(Tables.USERS_TABLE);
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildSaveUser() {
        return new SQL() {{
            INSERT_INTO(Tables.USERS_TABLE);
            VALUES(
                    "user_login, user_pass, user_email, user_status, user_registerdate, user_lastlogin",
                    "#{userName}, #{hashedPassword}, #{userMail}, #{isActive, typeHandler=com.blazarquant.bfp.database.typehandlers.user.ActiveUserTypeHandler}, " +
                            "#{registrationDate, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler}, " +
                            "#{lastLogin, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler}"
            );
        }}.toString();
    }

    public String buildUpdateConfirmationKey() {
        return new SQL() {{
            UPDATE(Tables.USERS_TABLE);
            SET("user_confirmationkey = #{confirmationKey}");
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildUpdateUserLastLogin() {
        return new SQL() {{
            UPDATE(Tables.USERS_TABLE);
            SET("user_lastlogin = #{lastLogin, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler}");
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildUpdateUserStatus() {
        return new SQL() {{
            UPDATE(Tables.USERS_TABLE);
            SET("user_status=#{userStatus, typeHandler=com.blazarquant.bfp.database.typehandlers.user.ActiveUserTypeHandler}");
            WHERE("ID = #{userId.id}");
        }}.toString();
    }

    public String buildFindParameters() {
        return new SQL() {{
            SELECT("user_setting, setting_value");
            FROM(Tables.USER_PARAMETERS);
            WHERE("user_id = #{userId.id}");
        }}.toString();
    }

    public String buildSaveUserRole() {
        return "INSERT INTO " + Tables.USER_ROLES_TABLE + " (user_id, role_id) SELECT #{userId.id}, ID FROM " + Tables.ROLES_TABLE + " WHERE role_name  = #{role.name}";
    }

    public String buildSaveUserPermission() {
        return "INSERT INTO " + Tables.USER_PERMISSIONS_TABLE + " (permission_id, user_id) SELECT id, #{userId.id} FROM "
                + Tables.PERMISSIONS_TABLE + " WHERE permission_name = #{permission}";
    }

    public String buildSaveParameter() {
        return "INSERT INTO " + Tables.USER_PARAMETERS + " (user_id, user_setting, setting_value) VALUES " +
                "(#{userId.id}, #{userSetting, typeHandler=com.blazarquant.bfp.database.typehandlers.user.UserSettingTypeHandler}, #{value}) " +
                "ON DUPLICATE KEY UPDATE user_id=VALUES(user_id), user_setting=VALUES(user_setting), setting_value=VALUES(setting_value)";
    }

}
