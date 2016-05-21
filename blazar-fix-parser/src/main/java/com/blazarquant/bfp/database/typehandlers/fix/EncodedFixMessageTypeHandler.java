package com.blazarquant.bfp.database.typehandlers.fix;

import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.security.util.SecurityUtilImpl;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.StringTypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class EncodedFixMessageTypeHandler extends StringTypeHandler {

    public final SecurityUtil securityUtil = new SecurityUtilImpl();

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, securityUtil.encodeMessage(s));
    }

    @Override
    public String getResult(ResultSet resultSet, String s) throws SQLException {
        return securityUtil.decodeMessage(resultSet.getString(s));
    }

    @Override
    public String getResult(ResultSet resultSet, int i) throws SQLException {
        return securityUtil.decodeMessage(resultSet.getString(i));
    }

    @Override
    public String getResult(CallableStatement callableStatement, int i) throws SQLException {
        return securityUtil.decodeMessage(callableStatement.getString(i));
    }

}
