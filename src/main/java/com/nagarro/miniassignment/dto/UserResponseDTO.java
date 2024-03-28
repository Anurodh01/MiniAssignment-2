package com.nagarro.miniassignment.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nagarro.miniassignment.entity.User;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public class UserResponseDTO {
	
	List<User> users;
	
	@Autowired
	public PageInfo pageInfo;
	
	
	
}
