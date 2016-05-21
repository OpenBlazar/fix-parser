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
