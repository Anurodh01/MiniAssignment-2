package com.nagarro.miniassignment.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.nagarro.miniassignment.dto.UserApiResponse;
import com.nagarro.miniassignment.entity.User;

public class Utility {
	
	public static List<User> getUserListFromUserApIResponse(UserApiResponse userApiResponse){
		
		List<User>  users = userApiResponse.getResults().stream().map(response -> {
			
			User user= new User();
			user.setFirstName(response.getName().getFirst());
			user.setLastName(response.getName().getLast());
			user.setAge(response.getDob().getAge());
			user.setGender(response.getGender());
			user.setDob(response.getDob().getDate());
			user.setNationality(response.getNat());
			user.setDateCreated(LocalDateTime.now());
			user.setDateModified(LocalDateTime.now());
			user.setName(response.getName().getFirst()+" "+ response.getName().getLast());
			
			return user;
		}).collect(Collectors.toList());
		
		return users;
	}
	
	public static List<User> formatDOB(List<User> users){
	
		SimpleDateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		SimpleDateFormat dateFormatter2= new SimpleDateFormat("dd MMM yyyy");
	
		List<User> users2= users.stream().map(user->{
			try {
				Date date= dateFormatter.parse(user.getDob());
				user.setDob(dateFormatter2.format(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return user;
		}).collect(Collectors.toList()); 
	
		return users2;
	}
	
}
