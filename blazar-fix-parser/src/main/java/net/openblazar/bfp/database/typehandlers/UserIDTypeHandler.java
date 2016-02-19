package net.openblazar.bfp.database.typehandlers;

import net.openblazar.bfp.data.user.UserID;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class UserIDTypeHandler implements TypeHandler<UserID> {

	@Override
	public void setParameter(PreparedStatement ps, int i, UserID parameter, JdbcType jdbcType) throws SQLException {
		// ignore for now
	}

	@Override
	public UserID getResult(ResultSet rs, String columnName) throws SQLException {
		return new UserID(rs.getLong(columnName));
	}

	@Override
	public UserID getResult(ResultSet rs, int columnIndex) throws SQLException {
		return new UserID(rs.getLong(columnIndex));
	}

	@Override
	public UserID getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return new UserID(cs.getLong(columnIndex));
	}

}
