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
