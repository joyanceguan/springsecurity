package com.joyance.springsecurity.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.joyance.springsecurity.model.UserStatus;


public class UserStatusTypeHandler extends BaseTypeHandler<UserStatus> {

	@Override
	public UserStatus getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		return UserStatus.valueOf(rs.getInt(columnName));
	}

	@Override
	public UserStatus getNullableResult(ResultSet rs, int columnIndex)
			throws SQLException {
		return UserStatus.valueOf(rs.getInt(columnIndex));
	}

	@Override
	public UserStatus getNullableResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return UserStatus.valueOf(cs.getInt(columnIndex));
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int parameterIndex,
			UserStatus parameter, JdbcType jdbcType) throws SQLException {
		ps.setInt(parameterIndex, parameter.getKey());
	}

}


