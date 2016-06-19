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

import com.blazarquant.bfp.data.user.UserSetting;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Wojciech Zankowski
 */
public class UserSettingTypeHandler implements TypeHandler<UserSetting> {

    @Override
    public void setParameter(PreparedStatement ps, int i, UserSetting parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public UserSetting getResult(ResultSet rs, String columnName) throws SQLException {
        return UserSetting.valueOf(rs.getString(columnName));
    }

    @Override
    public UserSetting getResult(ResultSet rs, int columnIndex) throws SQLException {
        return UserSetting.valueOf(rs.getString(columnIndex));
    }

    @Override
    public UserSetting getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return UserSetting.valueOf(cs.getString(columnIndex));
    }

}
