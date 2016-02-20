package net.openblazar.bfp.database.typehandlers.user;

import net.openblazar.bfp.data.user.UserState;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class ActiveUserTypeHandler implements TypeHandler<UserState> {

	@Override
	public void setParameter(PreparedStatement ps, int i, UserState parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(i, parameter.getState());
	}

	@Override
	public UserState getResult(ResultSet rs, String columnName) throws SQLException {
		return UserState.getUserStateFromCode(rs.getInt(columnName));
	}

	@Override
	public UserState getResult(ResultSet rs, int columnIndex) throws SQLException {
		return UserState.getUserStateFromCode(rs.getInt(columnIndex));
	}

	@Override
	public UserState getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return UserState.getUserStateFromCode(cs.getInt(columnIndex));
	}

}
