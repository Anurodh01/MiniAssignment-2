package com.nagarro.miniassignment.sortstrategy;

import java.util.List;

import org.springframework.stereotype.Component;

import com.nagarro.miniassignment.entity.User;

import lombok.Data;

@Data
@Component
public class Sort {

	private SortStrategy sortStrategy;
	
	public List<User> sort(List<User> users){
		return sortStrategy.sort(users);
	}
}
