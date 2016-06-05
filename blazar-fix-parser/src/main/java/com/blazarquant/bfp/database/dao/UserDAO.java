package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.user.*;
import com.blazarquant.bfp.database.typehandlers.InstantTypeHandler;
import com.blazarquant.bfp.database.typehandlers.user.ActiveUserTypeHandler;
import com.blazarquant.bfp.database.typehandlers.user.UserIDTypeHandler;
import com.blazarquant.bfp.database.typehandlers.user.UserSettingTypeHandler;
import com.blazarquant.bfp.database.utils.Tables;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.Instant;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface UserDAO {

    String SELECT_ALL = "SELECT * FROM " + Tables.USERS_TABLE;
    String SELECT_USER_BY_LOGIN = "SELECT * FROM " + Tables.USERS_TABLE + " WHERE user_login = #{userName}";
    String SELECT_USER_BY_EMAIL = "SELECT * FROM " + Tables.USERS_TABLE + " WHERE user_email = #{userMail}";
    String SELECT_USER_BY_ID = "SELECT * FROM " + Tables.USERS_TABLE + " WHERE ID = #{userId.id}";
    String SELECT_USER_ROLES = "SELECT role_name FROM " + Tables.USER_ROLES_TABLE + " JOIN " + Tables.ROLES_TABLE + " ON "
            + Tables.USER_ROLES_TABLE + ".role_id = " + Tables.ROLES_TABLE + ".ID WHERE user_id = #{userId.id}";
    String INSERT_USER_ROLE = "INSERT INTO " + Tables.USER_ROLES_TABLE + " (user_id, role_id) SELECT #{userId.id}, ID FROM " + Tables.ROLES_TABLE + " WHERE role_name  = #{role.name}";
    String SELECT_USER_PERMISSIONS = "SELECT permission_name FROM " + Tables.USER_PERMISSIONS_TABLE + " JOIN " + Tables.PERMISSIONS_TABLE + " ON "
            + Tables.USER_PERMISSIONS_TABLE + ".permission_id = " + Tables.PERMISSIONS_TABLE + ".id WHERE user_id = #{userId.id}";
    String INSERT_USER_PERMISSION = "INSERT INTO " + Tables.USER_PERMISSIONS_TABLE + " (permission_id, user_id) SELECT id, #{userId.id} FROM "
            + Tables.PERMISSIONS_TABLE + " WHERE permission_name = #{permission}";
    String CHECK_IF_USER_EXISTS = "SELECT count(1) FROM " + Tables.USERS_TABLE + " WHERE user_login = #{userName}";
    String CHECK_IF_USERMAIL_EXISTS = "SELECT count(1) FROM " + Tables.USERS_TABLE + " WHERE user_email = #{userMail}";
    String CHECK_IF_USER_ACTIVE = "SELECT user_status FROM " + Tables.USERS_TABLE + " WHERE user_login = #{userName}";
    String SELECT_USER_CONFIRMATION_KEY = "Select user_confirmationkey FROM " + Tables.USERS_TABLE + " WHERE ID = #{userID}";
    String INSERT_USER_REGISTER = "INSERT INTO " + Tables.USERS_TABLE + " (user_login, " +
            "user_pass, user_email, user_status, user_registerdate, user_lastlogin) VALUES " +
            "(#{userName}, #{hashedPassword}, #{userMail}, #{isActive, typeHandler=com.blazarquant.bfp.database.typehandlers.user.ActiveUserTypeHandler}, #{registrationDate, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler}, " +
            "#{lastLogin, typeHandler=com.blazarquant.bfp.database.typehandlers.InstantTypeHandler})";
    String UPDATE_USER_CONFIRMATION_KEY = "UPDATE " + Tables.USERS_TABLE + " SET user_confirmationkey=#{confirmationKey} WHERE ID = #{userID}";
    String UPDATE_USER_STATUS = "UPDATE " + Tables.USERS_TABLE + " SET user_status=#{userStatus, typeHandler=com.blazarquant.bfp.database.typehandlers.user.ActiveUserTypeHandler} WHERE ID = #{userID}";

    String SELECT_PARAMETERS = "SELECT user_setting, setting_value FROM " + Tables.USER_PARAMETERS + " WHERE user_id = #{userID.id}";
    String INSERT_PARAMETERS = "INSERT INTO " + Tables.USER_PARAMETERS + " (user_id, user_setting, setting_value) VALUES " +
            "(#{userID.id}, #{userSetting, typeHandler=com.blazarquant.bfp.database.typehandlers.user.UserSettingTypeHandler}, #{value}) " +
            "ON DUPLICATE KEY UPDATE user_id=VALUES(user_id), user_setting=VALUES(user_setting), setting_value=VALUES(setting_value)";

    @Select(SELECT_ALL)
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class)
    })
    List<UserDetails> findAllUsers();

    @Select(SELECT_USER_BY_LOGIN)
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserByLogin(
            @Param("userName") String userName
    );

    @Select(SELECT_USER_BY_EMAIL)
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserByMail(
            @Param("userMail") String userMail
    );

    @Select(SELECT_USER_BY_LOGIN)
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = Long.class, jdbcType = JdbcType.BIGINT),
    })
    Long findUserIDByLogin(
            @Param("userName") String userName
    );

    @Select(SELECT_USER_BY_ID)
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserById(
            @Param("userId") UserID userID
    );

    @Select(SELECT_USER_ROLES)
    @ConstructorArgs(value = {
            @Arg(column = "role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    List<Role> findUserRoles(
            @Param("userId") UserID userID
    );

    @Insert(INSERT_USER_ROLE)
    void saveUserRole(
            @Param("userId") UserID userID,
            @Param("role") Role role
    );

    @Select(SELECT_USER_PERMISSIONS)
    List<String> findUserPermissions(
            @Param("userId") UserID userID
    );

    @Insert(INSERT_USER_PERMISSION)
    void saveUserPermission(
            @Param("userId") UserID userID,
            @Param("permission") String permission
    );

    @Select(CHECK_IF_USER_EXISTS)
    Integer isUserNameExists(
            @Param("userName") String userName
    );

    @Select(CHECK_IF_USERMAIL_EXISTS)
    Integer isUserMailExists(
            @Param("userMail") String userMail
    );

    @Select(CHECK_IF_USER_ACTIVE)
    int isUserActive(
            @Param("userName") String userName
    );

    @Select(SELECT_USER_CONFIRMATION_KEY)
    String findConfirmationKeyFromUser(
            @Param("userID") long userID
    );

    @Insert(INSERT_USER_REGISTER)
    long saveUser(
            @Param("userName") String userName,
            @Param("userMail") String userMail,
            @Param("hashedPassword") String hashedPassword,
            @Param("isActive") UserState userState,
            @Param("registrationDate") Instant registrationDate,
            @Param("lastLogin") Instant lastLogin
    );

    @Update(UPDATE_USER_CONFIRMATION_KEY)
    void updateConfirmationKey(
            @Param("userID") long userID,
            @Param("confirmationKey") String confirmationKey
    );

    @Update(UPDATE_USER_STATUS)
    void updateUserStatus(
            @Param("userID") long userID,
            @Param("userStatus") UserState userState
    );

    @Select(SELECT_PARAMETERS)
    @ConstructorArgs(value = {
            @Arg(column = "user_setting", jdbcType = JdbcType.VARCHAR, javaType = UserSetting.class, typeHandler = UserSettingTypeHandler.class),
            @Arg(column = "setting_value", jdbcType = JdbcType.VARCHAR, javaType = String.class)
    })
    List<UserSettingHolder> findParameters(
            @Param("userID") UserID userID
    );

    @Insert(INSERT_PARAMETERS)
    void saveParameter(
            @Param("userID") UserID userID,
            @Param("userSetting") UserSetting userSetting,
            @Param("value") String value
    );
}
