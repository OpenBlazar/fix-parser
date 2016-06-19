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
package com.blazarquant.bfp.database.typehandlers.user;

import com.blazarquant.bfp.data.user.UserState;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class ActiveUserTypeHandler implements TypeHandler<UserState> {

    @Override
    public void setParameter(PreparedStatement ps, int i, UserState parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getState());
    }

    @Override
    public UserState getResult(ResultSet rs, String columnName) throws SQLException {
        return UserState.getUserStateFromCode(rs.getInt(columnName));
    }

    @Override
    public UserState getResult(ResultSet rs, int columnIndex) throws SQLException {
        return UserState.getUserStateFromCode(rs.getInt(columnIndex));
    }

    @Override
    public UserState getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return UserState.getUserStateFromCode(cs.getInt(columnIndex));
    }

}
