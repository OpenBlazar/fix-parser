package net.openblazar.bfp.database.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author Wojciech Zankowski
 */
public class LocalDateTimeTypeHandler implements TypeHandler<Instant> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Instant parameter, JdbcType
			jdbcType) throws SQLException {
		// ignore for now
	}

	@Override
	public Instant getResult(ResultSet rs, String columnName) throws SQLException {
		return rs.getTimestamp(columnName).toInstant();
	}

	@Override
	public Instant getResult(ResultSet rs, int columnIndex) throws SQLException {
		return rs.getTimestamp(columnIndex).toInstant();
	}

	@Override
	public Instant getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return cs.getTimestamp(columnIndex).toInstant();
	}

}
