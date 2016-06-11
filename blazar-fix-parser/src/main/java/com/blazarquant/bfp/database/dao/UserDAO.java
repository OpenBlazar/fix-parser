package com.blazarquant.bfp.database.dao;

import com.blazarquant.bfp.data.user.*;
import com.blazarquant.bfp.database.providers.UserSQLProvider;
import com.blazarquant.bfp.database.typehandlers.InstantTypeHandler;
import com.blazarquant.bfp.database.typehandlers.user.ActiveUserTypeHandler;
import com.blazarquant.bfp.database.typehandlers.user.UserIDTypeHandler;
import com.blazarquant.bfp.database.typehandlers.user.UserSettingTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.time.Instant;
import java.util.List;

/**
 * @author Wojciech Zankowski
 */
public interface UserDAO {

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindAllUsers")
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class)
    })
    List<UserDetails> findAllUsers();

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserByLogin")
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserByLogin(
            @Param("userName") String userName
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserByMail")
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserByMail(
            @Param("userMail") String userMail
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserIDByLogin")
    @ConstructorArgs(value = {
            @Arg(column = "ID", javaType = long.class, jdbcType = JdbcType.BIGINT),
    })
    UserID findUserIDByLogin(
            @Param("userName") String userName
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserById")
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserById(
            @Param("userId") UserID userID
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserRoles")
    @ConstructorArgs(value = {
            @Arg(column = "role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    List<Role> findUserRoles(
            @Param("userId") UserID userID
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveUserRole")
    void saveUserRole(
            @Param("userId") UserID userID,
            @Param("role") Role role
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserPermissions")
    List<String> findUserPermissions(
            @Param("userId") UserID userID
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveUserPermission")
    void saveUserPermission(
            @Param("userId") UserID userID,
            @Param("permission") String permission
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildIsUserNameExists")
    Integer isUserNameExists(
            @Param("userName") String userName
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildIsUserMailExists")
    Integer isUserMailExists(
            @Param("userMail") String userMail
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildIsUserActive")
    int isUserActive(
            @Param("userName") String userName
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindConfirmationKeyFromUser")
    String findConfirmationKeyFromUser(
            @Param("userId") UserID userID
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveUser")
    long saveUser(
            @Param("userName") String userName,
            @Param("userMail") String userMail,
            @Param("hashedPassword") String hashedPassword,
            @Param("isActive") UserState userState,
            @Param("registrationDate") Instant registrationDate,
            @Param("lastLogin") Instant lastLogin
    );

    @UpdateProvider(type = UserSQLProvider.class, method = "buildUpdateConfirmationKey")
    void updateConfirmationKey(
            @Param("userId") UserID userID,
            @Param("confirmationKey") String confirmationKey
    );

    @UpdateProvider(type = UserSQLProvider.class, method = "buildUpdateUserStatus")
    void updateUserStatus(
            @Param("userId") UserID userID,
            @Param("userStatus") UserState userState
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindParameters")
    @ConstructorArgs(value = {
            @Arg(column = "user_setting", jdbcType = JdbcType.VARCHAR, javaType = UserSetting.class, typeHandler = UserSettingTypeHandler.class),
            @Arg(column = "setting_value", jdbcType = JdbcType.VARCHAR, javaType = String.class)
    })
    List<UserSettingHolder> findParameters(
            @Param("userId") UserID userID
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveParameter")
    void saveParameter(
            @Param("userId") UserID userID,
            @Param("userSetting") UserSetting userSetting,
            @Param("value") String value
    );
}
