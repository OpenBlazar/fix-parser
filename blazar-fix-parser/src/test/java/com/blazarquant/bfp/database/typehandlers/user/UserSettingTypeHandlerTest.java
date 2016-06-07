package com.blazarquant.bfp.database.typehandlers.user;

import com.blazarquant.bfp.data.user.UserSetting;
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
public class UserSettingTypeHandlerTest {

    private TypeHandler<UserSetting> typeHandler;

    @Before
    public void setUp() {
        typeHandler = new UserSettingTypeHandler();
    }

    @Test
    public void testResultSetGetters() throws SQLException {
        int columnIndex = 5;
        String columnName = "user_setting";
        UserSetting expectedUserSetting = UserSetting.DEFAULT_PROVIDER;

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString(columnIndex)).thenReturn(expectedUserSetting.name());
        when(resultSet.getString(columnName)).thenReturn(expectedUserSetting.name());

        UserSetting userSetting = typeHandler.getResult(resultSet, columnIndex);
        assertEquals(expectedUserSetting, userSetting);

        userSetting = typeHandler.getResult(resultSet, columnName);
        assertEquals(expectedUserSetting, userSetting);
    }


    @Test
    public void testCallableStatementGetter() throws SQLException {
        int columnIndex = 5;
        UserSetting expectedUserSetting = UserSetting.STORE_MESSAGES;

        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getString(columnIndex)).thenReturn(expectedUserSetting.name());

        UserSetting userSetting = typeHandler.getResult(callableStatement, columnIndex);
        assertEquals(expectedUserSetting, userSetting);
    }

    @Test
    public void testPreparedStatementSetter() throws SQLException {
        int columnIndex = 1;
        UserSetting expectedUserSetting = UserSetting.DEFAULT_PROVIDER;

        final ArgumentCaptor<String> userSettingCaptor = ArgumentCaptor.forClass(String.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        typeHandler.setParameter(preparedStatement, columnIndex, expectedUserSetting, JdbcType.TIMESTAMP);

        verify(preparedStatement).setString(eq(columnIndex), userSettingCaptor.capture());
        assertEquals(expectedUserSetting.name(), userSettingCaptor.getValue());
    }

}
