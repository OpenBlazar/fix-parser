package pl.zankowski.fixparser.messages.fix;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import pl.zankowski.fixparser.messages.entity.parser.FixMessage;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static pl.zankowski.fixparser.core.framework.CodecUtil.encodeBase64;

public class FixMessageTypeHandler implements TypeHandler<FixMessage> {

    private final FixMessageConverter messageConverter = new FixMessageConverter();
    private final FixMessageMapper mapper = new FixMessageMapper();

    @Override
    public void setParameter(PreparedStatement ps, int i, FixMessage message, JdbcType jdbcType) throws SQLException {
        ps.setString(i, encodeBase64(messageConverter.convertToString(mapper.map(message))));
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
