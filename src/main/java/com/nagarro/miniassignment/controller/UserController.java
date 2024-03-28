package com.nagarro.miniassignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.miniassignment.dto.UserResponseDTO;
import com.nagarro.miniassignment.entity.User;
import com.nagarro.miniassignment.exception.PageNotFoundException;
import com.nagarro.miniassignment.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/users")
	public ResponseEntity<List<User>> createUser(@RequestParam(value="size", required=true) int size){
	
		List<User> users = userService.createUser(size);
		return new ResponseEntity<List<User>>(users, HttpStatus.CREATED);
	}
	
	@GetMapping("/users")
	public ResponseEntity<UserResponseDTO> getUsers(
			@RequestParam(value="sortType", required=true) String sortType,
			@RequestParam(value="sortOrder", required= true) String sortOrder, 
			@RequestParam(value="limit", required=true) int limit,
			@RequestParam(value="offset", required= true)  int offset){
		

		UserResponseDTO users= userService.getUsers(sortType, sortOrder, limit, offset);
		return new ResponseEntity<UserResponseDTO>(users, HttpStatus.OK);	
	}
	
	//Mapping for Invalid URL request
	
	@GetMapping("/**")
	public void handleUndefinedPath() {
		throw new PageNotFoundException("No URL mapping found!!");
	}
	
	@PostMapping("/**")
	public void handle() {
		throw new PageNotFoundException("No URL mapping found!!");
	}
	
	
}
