package com.nagarro.miniassignment.sortstrategy;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nagarro.miniassignment.entity.User;

@Component
public class AgeOddFirstStrategy implements SortStrategy{

	@Override
	public List<User> sort(List<User> users) {
		
		List<User> sortedUsers=users.stream().sorted(Comparator.comparing(user ->  ((User) user).getAge()%2 ==0? 1: 0).thenComparing(user -> ((User) user).getAge()).thenComparing(user-> (((User) user).getName()))).collect(Collectors.toList());
		return sortedUsers;
		
	}
	
	
}
