package com.blazarquant.bfp.database.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.time.Instant;

/**
 * @author Wojciech Zankowski
 */
public class InstantTypeHandler implements TypeHandler<Instant> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType) throws SQLException {
		ps.setTimestamp(i, new Timestamp(parameter.toEpochMilli()));
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
