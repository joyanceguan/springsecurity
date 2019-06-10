package com.joyance.springsecurity.mapper;

import com.joyance.springsecurity.persistence.User;

public interface UserMapper {

	public User selectUserByUsername(String username);
	
	public int save(User user);
	
}
