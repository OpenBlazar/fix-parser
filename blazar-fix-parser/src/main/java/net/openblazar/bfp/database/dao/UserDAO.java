package net.openblazar.bfp.database.dao;

import net.openblazar.bfp.data.user.Role;
import net.openblazar.bfp.data.user.UserDetails;
import net.openblazar.bfp.data.user.UserID;
import net.openblazar.bfp.data.user.UserState;
import net.openblazar.bfp.database.typehandlers.LocalDateTimeTypeHandler;
import net.openblazar.bfp.database.typehandlers.user.ActiveUserTypeHandler;
import net.openblazar.bfp.database.typehandlers.user.UserIDTypeHandler;
import net.openblazar.bfp.database.utils.Tables;
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
	String SELECT_USER_BY_ID = "SELECT * FROM " + Tables.USERS_TABLE + " WHERE ID = #{userId}";
	String SELECT_USER_ROLES = "SELECT role_name FROM " + Tables.USER_ROLES_TABLE + " JOIN " + Tables.ROLES_TABLE + " ON "
			+ Tables.USER_ROLES_TABLE + ".role_id = "+ Tables.ROLES_TABLE+".ID WHERE user_id = #{id}";
	String CHECK_IF_USER_EXISTS = "SELECT count(1) FROM " + Tables.USERS_TABLE + " WHERE user_login = #{userName}";
	String INSERT_USER_REGISTER = "INSERT INTO " + Tables.USERS_TABLE + " (user_login, " +
			"user_pass, user_email, user_status, user_registerdate, user_lastlogin) VALUES " +
			"(#{userName}, #{hashedPassword}, #{userMail}, #{isActive, typeHandler=ActiveUserTypeHandler.class}, #{registrationDate}, " +
			"#{lastLogin})";

	@Select(SELECT_ALL)
	@ConstructorArgs(value = {
			@Arg(column="id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
			@Arg(column="user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_status", javaType = Boolean.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
			@Arg(column="user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTimeTypeHandler.class),
			@Arg(column="user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTimeTypeHandler.class)
	})
	List<UserDetails> findAllUsers();

	@Select(SELECT_USER_BY_LOGIN)
	@ConstructorArgs(value = {
			@Arg(column="id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
			@Arg(column="user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_status", javaType = Boolean.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
			@Arg(column="user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTimeTypeHandler.class),
			@Arg(column="user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTimeTypeHandler.class)
	})
	UserDetails findUserByLogin(String userName);

	@Select(SELECT_USER_BY_ID)
	@ConstructorArgs(value = {
			@Arg(column="id", javaType = UserID.class, jdbcType = JdbcType.BIGINT, typeHandler = UserIDTypeHandler.class),
			@Arg(column="user_login", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_email", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_pass", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Arg(column="user_status", javaType = Boolean.class, jdbcType = JdbcType.INTEGER, typeHandler = ActiveUserTypeHandler.class),
			@Arg(column="user_registerdate", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTimeTypeHandler.class),
			@Arg(column="user_lastlogin", javaType = Instant.class, jdbcType = JdbcType.DATE, typeHandler = LocalDateTimeTypeHandler.class)
	})
	UserDetails findUserById(UserID userID);

	@Select(SELECT_USER_ROLES)
	@ConstructorArgs(value = {
			@Arg(column="role_name", javaType = String.class, jdbcType = JdbcType.VARCHAR)
	})
	List<Role> findUserRoles(UserID userID);

	@Select(CHECK_IF_USER_EXISTS)
	Integer isUserExists(String userName);

	@Insert(INSERT_USER_REGISTER)
	void saveUser(
			@Param("userName") String userName,
			@Param("userMail") String userMail,
			@Param("hashedPassword") String hashedPassword,
			@Param("isActive") UserState userState,
			@Param("registrationDate") String registrationDate,
			@Param("lastLogin") String lastLogin);
}
