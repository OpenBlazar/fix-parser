package net.openblazar.bfp.database.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class ActiveUserTypeHandler implements TypeHandler<Boolean> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Boolean parameter, JdbcType jdbcType) throws SQLException {
		// ignore for now
	}

	@Override
	public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getInt(columnName) == 1;
	}

	@Override
	public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getInt(columnIndex) == 1;
	}

	@Override
	public Boolean getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getInt(columnIndex) == 1;
	}

}
