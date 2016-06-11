package com.blazarquant.bfp.database.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

/**
 * @author Wojciech Zankowski
 */
public class InstantTypeHandler implements TypeHandler<Instant> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.toEpochMilli());
    }

    @Override
    public Instant getResult(ResultSet rs, String columnName) throws SQLException {
        return Instant.ofEpochMilli(rs.getLong(columnName));
    }

    @Override
    public Instant getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Instant.ofEpochMilli(rs.getLong(columnIndex));
    }

    @Override
    public Instant getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Instant.ofEpochMilli(cs.getLong(columnIndex));
    }

}
