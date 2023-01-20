package com.blog.applicatication.services;


import com.blog.applicatication.payload.UserDto;
import com.blog.applicatication.payload.UserResponse;

public interface UserService {

	UserDto registerNewUser(UserDto userDto);
	
	UserResponse getAllUser(int pageNo, int pageSize, String sortBy, String sortDir);
	
	UserDto getUserById(int userId);
	
	UserDto updateUserById(int userId, UserDto userDto);
	
	void deleteUserById(int userId);
}
