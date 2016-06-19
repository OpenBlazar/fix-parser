/*
 * Copyright 2016 Wojciech Zankowski.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blazarquant.bfp.database.typehandlers;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

/**
 * @author Wojciech Zankowski
 */
public class InstantTypeHandler implements TypeHandler<Instant> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.toEpochMilli());
    }

    @Override
    public Instant getResult(ResultSet rs, String columnName) throws SQLException {
        return Instant.ofEpochMilli(rs.getLong(columnName));
    }

    @Override
    public Instant getResult(ResultSet rs, int columnIndex) throws SQLException {
        return Instant.ofEpochMilli(rs.getLong(columnIndex));
    }

    @Override
    public Instant getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Instant.ofEpochMilli(cs.getLong(columnIndex));
    }

}
