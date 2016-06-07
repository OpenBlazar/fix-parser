package com.blazarquant.bfp.database.typehandlers.user;

import com.blazarquant.bfp.data.user.UserState;
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
public class ActiveUserTypeHandlerTest {

    private TypeHandler<UserState> typeHandler;

    @Before
    public void setUp() {
        typeHandler = new ActiveUserTypeHandler();
    }

    @Test
    public void testResultSetGetters() throws SQLException {
        int columnIndex = 5;
        String columnName = "user_state";
        UserState expectedUserState = UserState.ACTIVE;

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(columnIndex)).thenReturn(expectedUserState.getState());
        when(resultSet.getInt(columnName)).thenReturn(expectedUserState.getState());

        UserState userState = typeHandler.getResult(resultSet, columnIndex);
        assertEquals(expectedUserState, userState);

        userState = typeHandler.getResult(resultSet, columnName);
        assertEquals(expectedUserState, userState);
    }


    @Test
    public void testCallableStatementGetter() throws SQLException {
        int columnIndex = 5;
        UserState expectedUserState = UserState.INACTIVE;

        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getInt(columnIndex)).thenReturn(expectedUserState.getState());

        UserState userState = typeHandler.getResult(callableStatement, columnIndex);
        assertEquals(expectedUserState, userState);
    }

    @Test
    public void testPreparedStatementSetter() throws SQLException {
        int columnIndex = 1;
        UserState expectedUserState = UserState.ACTIVE;

        final ArgumentCaptor<Integer> userStateCaptor = ArgumentCaptor.forClass(Integer.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        typeHandler.setParameter(preparedStatement, columnIndex, expectedUserState, JdbcType.TIMESTAMP);

        verify(preparedStatement).setInt(eq(columnIndex), userStateCaptor.capture());
        assertEquals(Integer.valueOf(expectedUserState.getState()), userStateCaptor.getValue());
    }


}
