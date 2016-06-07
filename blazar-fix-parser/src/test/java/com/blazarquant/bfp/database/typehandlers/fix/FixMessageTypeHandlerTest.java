package com.blazarquant.bfp.database.typehandlers.fix;

import com.blazarquant.bfp.data.user.UserSetting;
import com.blazarquant.bfp.fix.data.*;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Wojciech Zankowski
 */
public class FixMessageTypeHandlerTest {

    private TypeHandler<FixMessage> typeHandler;

    @Before
    public void setUp() {
        typeHandler = new FixMessageTypeHandler();
    }

    @Test
    public void testResultSetGetters() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        try {
            typeHandler.getResult(resultSet, 1);
            fail();
        } catch (UnsupportedOperationException e) {
            assertEquals("Getters for FixMessageTypeHandler not supported.", e.getMessage());
        }
        try {
            typeHandler.getResult(resultSet, "col");
        } catch (UnsupportedOperationException e) {
            assertEquals("Getters for FixMessageTypeHandler not supported.", e.getMessage());
        }
    }

    @Test
    public void testCallableStatementGetter() throws SQLException {
        CallableStatement callableStatement = mock(CallableStatement.class);
        try {
            typeHandler.getResult(callableStatement, 1);
        } catch (UnsupportedOperationException e) {
            assertEquals("Getters for FixMessageTypeHandler not supported.", e.getMessage());
        }
    }

    /**
     * 8=FIX.4.2#35=0#
     */
    @Test
    public void testPreparedStatementSetter() throws SQLException {
        int columnIndex = 1;

        FixPair beginString = new FixPair(8, new FixField(8, "BeginString"), new FixValue("FIX.4.2"));
        FixPair msgType = new FixPair(35, new FixField(35, "MsgType"), new FixValue("0"));
        List<FixPair> fixPairList = Arrays.asList(beginString, msgType);
        FixMessage expectedFixMessage = new FixMessage(0L, FixVersion.FIX_42, msgType, fixPairList);

        final ArgumentCaptor<String> fixMessageCaptor = ArgumentCaptor.forClass(String.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        typeHandler.setParameter(preparedStatement, columnIndex, expectedFixMessage, JdbcType.VARCHAR);

        verify(preparedStatement).setString(eq(columnIndex), fixMessageCaptor.capture());
        assertEquals("OD1GSVguNC4yATM1PTAB", fixMessageCaptor.getValue());

    }

}
