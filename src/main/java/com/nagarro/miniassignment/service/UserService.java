package com.nagarro.miniassignment.service;

import java.util.List;

import com.nagarro.miniassignment.dto.UserResponseDTO;
import com.nagarro.miniassignment.entity.User;


public interface UserService {
	
	List<User> createUser(int size);
	
	UserResponseDTO getUsers(String sortType, String sortOrder,  int limit, int offset);
	
}
