package pl.zankowski.fixparser.user.handler;

import pl.zankowski.fixparser.user.api.UserId;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserIDTypeHandler implements TypeHandler<UserId> {

    @Override
    public void setParameter(PreparedStatement ps, int i, UserId parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getId());
    }

    @Override
    public UserId getResult(ResultSet rs, String columnName) throws SQLException {
        return new UserId(rs.getLong(columnName));
    }

    @Override
    public UserId getResult(ResultSet rs, int columnIndex) throws SQLException {
        return new UserId(rs.getLong(columnIndex));
    }

    @Override
    public UserId getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return new UserId(cs.getLong(columnIndex));
    }

}
