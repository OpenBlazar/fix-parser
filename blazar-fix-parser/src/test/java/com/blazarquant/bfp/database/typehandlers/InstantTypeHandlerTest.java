package com.blazarquant.bfp.database.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.sql.*;
import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Wojciech Zankowski
 */
public class InstantTypeHandlerTest {

    TypeHandler<Instant> typeHandler;

    @Before
    public void setUp() {
        typeHandler = new InstantTypeHandler();
    }

    @Test
    public void testResultSetGetters() throws SQLException {
        int columnIndex = 5;
        String columnName = "time";
        final long timestamp = 12345;
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getLong(columnIndex)).thenReturn(timestamp);
        when(resultSet.getLong(columnName)).thenReturn(timestamp);

        Instant result = typeHandler.getResult(resultSet, columnIndex);
        assertEquals(timestamp, result.toEpochMilli());

        result = typeHandler.getResult(resultSet, columnName);
        assertEquals(timestamp, result.toEpochMilli());
    }

    @Test
    public void testCallableStatementGetter() throws SQLException {
        int columnIndex = 5;
        final long timestamp = 12345;
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getLong(columnIndex)).thenReturn(timestamp);

        Instant result = typeHandler.getResult(callableStatement, columnIndex);
        assertEquals(timestamp, result.toEpochMilli());
    }

    @Test
    public void testPreparedStatementSetter() throws SQLException {
        int columnIndex = 1;
        final long timestamp = 12345;

        final ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        typeHandler.setParameter(preparedStatement, columnIndex, Instant.ofEpochMilli(timestamp), JdbcType.TIMESTAMP);

        verify(preparedStatement).setLong(eq(columnIndex), timestampCaptor.capture());
        assertEquals(Long.valueOf(timestamp), timestampCaptor.getValue());
    }


}
