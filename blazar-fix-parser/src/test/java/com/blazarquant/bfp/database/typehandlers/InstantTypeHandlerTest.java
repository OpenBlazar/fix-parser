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
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getTimestamp(columnIndex)).thenReturn(new Timestamp(0));
        when(resultSet.getTimestamp(columnName)).thenReturn(new Timestamp(0));

        Instant result = typeHandler.getResult(resultSet, columnIndex);
        assertEquals(0, result.toEpochMilli());

        result = typeHandler.getResult(resultSet, columnName);
        assertEquals(0, result.toEpochMilli());
    }

    @Test
    public void testCallableStatementGetter() throws SQLException {
        int columnIndex = 5;
        CallableStatement callableStatement = mock(CallableStatement.class);
        when(callableStatement.getTimestamp(columnIndex)).thenReturn(new Timestamp(0));

        Instant result = typeHandler.getResult(callableStatement, columnIndex);
        assertEquals(0, result.toEpochMilli());
    }

    @Test
    public void testPreparedStatementSetter() throws SQLException {
        int columnIndex = 1;

        final ArgumentCaptor<Timestamp> timestampCaptor = ArgumentCaptor.forClass(Timestamp.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        typeHandler.setParameter(preparedStatement, columnIndex, Instant.ofEpochMilli(0), JdbcType.TIMESTAMP);
        
        verify(preparedStatement).setTimestamp(eq(columnIndex), timestampCaptor.capture());
        assertEquals(new Timestamp(0), timestampCaptor.getValue());
    }


}
