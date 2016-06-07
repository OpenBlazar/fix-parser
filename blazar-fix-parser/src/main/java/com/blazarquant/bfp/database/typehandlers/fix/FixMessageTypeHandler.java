package com.blazarquant.bfp.database.typehandlers.fix;

import com.blazarquant.bfp.core.security.util.SecurityUtil;
import com.blazarquant.bfp.core.security.util.SecurityUtilImpl;
import com.blazarquant.bfp.fix.data.FixMessage;
import com.blazarquant.bfp.fix.parser.util.FixMessageConverter;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageTypeHandler implements TypeHandler<FixMessage> {

    private final FixMessageConverter messageConverter = new FixMessageConverter();
    private final SecurityUtil securityUtil = new SecurityUtilImpl();

    @Override
    public void setParameter(PreparedStatement ps, int i, FixMessage message, JdbcType jdbcType) throws SQLException {
        ps.setString(i, securityUtil.encodeMessage(messageConverter.convertToString(message)));
    }

    @Override
    public FixMessage getResult(ResultSet rs, String columnName) throws SQLException {
        throw new UnsupportedOperationException("Getters for FixMessageTypeHandler not supported.");
    }

    @Override
    public FixMessage getResult(ResultSet rs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Getters for FixMessageTypeHandler not supported.");
    }

    @Override
    public FixMessage getResult(CallableStatement cs, int columnIndex) throws SQLException {
        throw new UnsupportedOperationException("Getters for FixMessageTypeHandler not supported.");
    }

}
