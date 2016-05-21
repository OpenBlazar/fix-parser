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

    private static final String ID_COLUMN = "ID";

    private final FixMessageConverter messageConverter = new FixMessageConverter();
    private final SecurityUtil securityUtil = new SecurityUtilImpl();

    @Override
    public void setParameter(PreparedStatement ps, int i, FixMessage message, JdbcType jdbcType) throws SQLException {
        ps.setString(i, securityUtil.encodeMessage(messageConverter.convertToString(message)));
    }

    @Override
    public FixMessage getResult(ResultSet rs, String columnName) throws SQLException {
        Long messageID = rs.getLong(ID_COLUMN);
//        return messageConverter.convertToFixMessage(
//                securityUtil.decodeMessage(rs.getString(columnName)),
//                FixMessageConverter.FIELD_DELIMITER,
//                messageID);
        return null;
    }

    @Override
    public FixMessage getResult(ResultSet rs, int columnIndex) throws SQLException {
        Long messageID = rs.getLong(ID_COLUMN);
//        return messageConverter.convertToFixMessage(
//                securityUtil.decodeMessage(rs.getString(columnIndex)),
//                FixMessageConverter.FIELD_DELIMITER,
//                messageID);
        return null;
    }

    @Override
    public FixMessage getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long messageID = cs.getLong(ID_COLUMN);
//        return messageConverter.convertToFixMessage(
//                securityUtil.decodeMessage(cs.getString(columnIndex)),
//                FixMessageConverter.FIELD_DELIMITER,
//                messageID);
        return null;
    }

}
