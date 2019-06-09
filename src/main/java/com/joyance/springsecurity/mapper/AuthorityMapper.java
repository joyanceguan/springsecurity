package com.joyance.springsecurity.mapper;

import java.util.List;

import com.joyance.springsecurity.persistence.Authority;

public interface AuthorityMapper {

	public List<Authority> selectByRoleId(Integer roleId);
}
