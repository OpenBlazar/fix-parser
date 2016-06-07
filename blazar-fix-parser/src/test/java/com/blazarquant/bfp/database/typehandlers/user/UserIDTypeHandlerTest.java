package com.blazarquant.bfp.database.typehandlers.user;

import com.blazarquant.bfp.data.user.UserID;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Wojciech Zankowski
 */
public class UserIDTypeHandlerTest {

    TypeHandler<UserID> typeHandler;

    @Before
    public void setUp() {
        typeHandler = new UserIDTypeHandler();
    }

    @Test
    public void testResultSetGetters() throws SQLException {
        int columnIndex = 5;
        String columnName = "userId";
        UserID expectedUserID = new UserID(9);

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(columnIndex)).thenReturn(expectedUserID.getId());
        when(resultSet.getLong(columnName)).thenReturn(expectedUserID.getId());

        UserID userID = typeHandler.getResult(resultSet, columnIndex);
        assertEquals(expectedUserID, userID);

        userID = typeHandler.getResult(resultSet, columnName);
        assertEquals(expectedUserID, userID);
    }

    @Test
    public void testCallableStatementGetter() throws SQLException {
        int columnIndex = 5;
        UserID expectedUserID = new UserID(9);

        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getLong(columnIndex)).thenReturn(expectedUserID.getId());

        UserID userID = typeHandler.getResult(callableStatement, columnIndex);
        assertEquals(expectedUserID, userID);
    }

    @Test
    public void testPreparedStatementSetter() throws SQLException {
        int columnIndex = 1;
        UserID expectedUserID = new UserID(9);

        final ArgumentCaptor<Long> userIDCaptor = ArgumentCaptor.forClass(Long.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        typeHandler.setParameter(preparedStatement, columnIndex, expectedUserID, JdbcType.TIMESTAMP);

        verify(preparedStatement).setLong(eq(columnIndex), userIDCaptor.capture());
        assertEquals(Long.valueOf(expectedUserID.getId()), userIDCaptor.getValue());
    }

}
