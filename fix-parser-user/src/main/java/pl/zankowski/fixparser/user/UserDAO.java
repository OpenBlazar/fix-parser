package pl.zankowski.fixparser.user;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.InstantTypeHandler;
import org.apache.ibatis.type.JdbcType;
import pl.zankowski.fixparser.core.entity.IRepository;
import pl.zankowski.fixparser.user.api.Role;
import pl.zankowski.fixparser.user.entity.UserDetails;
import pl.zankowski.fixparser.user.api.UserId;
import pl.zankowski.fixparser.user.entity.UserSetting;
import pl.zankowski.fixparser.user.entity.UserSettingHolder;
import pl.zankowski.fixparser.user.api.UserState;
import pl.zankowski.fixparser.user.handler.ActiveUserTypeHandler;
import pl.zankowski.fixparser.user.handler.UserIDTypeHandler;
import pl.zankowski.fixparser.user.handler.UserSettingTypeHandler;

import java.time.Instant;
import java.util.List;

public interface UserDAO extends IRepository {

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindAllUsers")
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserId.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
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
            @Arg(column = "id", javaType = UserId.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
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
            @Arg(column = "id", javaType = UserId.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
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
    UserId findUserIDByLogin(
            @Param("userName") String userName
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserById")
    @ConstructorArgs(value = {
            @Arg(column = "id", javaType = UserId.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
            @Arg(column = "user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "user_status", javaType = UserState.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
            @Arg(column = "user_registerdate", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class),
            @Arg(column = "user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.BIGINT, typeHandler = InstantTypeHandler.class)
    })
    UserDetails findUserById(
            @Param("userId") UserId userId
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserRoles")
    @ConstructorArgs(value = {
            @Arg(column = "role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    List<Role> findUserRoles(
            @Param("userId") UserId userId
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveUserRole")
    void saveUserRole(
            @Param("userId") UserId userId,
            @Param("role") Role role
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindUserPermissions")
    List<String> findUserPermissions(
            @Param("userId") UserId userId
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveUserPermission")
    void saveUserPermission(
            @Param("userId") UserId userId,
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
    Integer isUserActive(
            @Param("userName") String userName
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindConfirmationKeyFromUser")
    String findConfirmationKeyFromUser(
            @Param("userId") UserId userId
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveUser")
    Long saveUser(
            @Param("userName") String userName,
            @Param("userMail") String userMail,
            @Param("hashedPassword") String hashedPassword,
            @Param("isActive") UserState userState,
            @Param("registrationDate") Instant registrationDate,
            @Param("lastLogin") Instant lastLogin
    );

    @UpdateProvider(type = UserSQLProvider.class, method = "buildUpdateConfirmationKey")
    void updateConfirmationKey(
            @Param("userId") UserId userId,
            @Param("confirmationKey") String confirmationKey
    );

    @UpdateProvider(type = UserSQLProvider.class, method = "buildUpdateUserLastLogin")
    void updateLastLogin(
            @Param("userId") UserId userId,
            @Param("lastLogin") Instant lastLogin
    );

    @UpdateProvider(type = UserSQLProvider.class, method = "buildUpdateUserStatus")
    void updateUserStatus(
            @Param("userId") UserId userId,
            @Param("userStatus") UserState userState
    );

    @SelectProvider(type = UserSQLProvider.class, method = "buildFindParameters")
    @ConstructorArgs(value = {
            @Arg(column = "user_setting", jdbcType = JdbcType.VARCHAR, javaType = UserSetting.class, typeHandler = UserSettingTypeHandler.class),
            @Arg(column = "setting_value", jdbcType = JdbcType.VARCHAR, javaType = String.class)
    })
    List<UserSettingHolder> findParameters(
            @Param("userId") UserId userId
    );

    @InsertProvider(type = UserSQLProvider.class, method = "buildSaveParameter")
    void saveParameter(
            @Param("userId") UserId userId,
            @Param("userSetting") UserSetting userSetting,
            @Param("value") String value
    );
}
