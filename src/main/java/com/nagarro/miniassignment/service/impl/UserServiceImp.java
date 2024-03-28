package com.nagarro.miniassignment.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nagarro.miniassignment.dao.UserRespository;
import com.nagarro.miniassignment.dto.UserApiResponse;
import com.nagarro.miniassignment.dto.UserResponseDTO;
import com.nagarro.miniassignment.entity.User;
import com.nagarro.miniassignment.entity.VerificationStatus;
import com.nagarro.miniassignment.executors.NationalityAndGenderAPIExecutorService;
import com.nagarro.miniassignment.executors.UserApiExecutorService;
import com.nagarro.miniassignment.service.UserService;
import com.nagarro.miniassignment.sortstrategy.Sort;
import com.nagarro.miniassignment.sortstrategy.SortStrategy;
import com.nagarro.miniassignment.sortstrategy.SortStrategyFactory;
import com.nagarro.miniassignment.utils.Utility;
import com.nagarro.miniassignment.validators.Validator;
import com.nagarro.miniassignment.validators.ValidatorFactory;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRespository userRepo;
	
	@Autowired
	private UserApiExecutorService userApiExecutorService;
	
	@Autowired
	private NationalityAndGenderAPIExecutorService nationalityAndGenderAPIExecutorService;
	
	@Autowired
	private Sort sortContext;
	
	@Autowired
	private SortStrategyFactory sortStrategyfactory;
	
	@Autowired
	private UserResponseDTO userResponseDTO;
	
	@Autowired
	private ValidatorFactory validatorFactory;

	
	@Override
	public List<User> createUser(int size) {
		
		//validation
		
		Validator validator= validatorFactory.getValidator("numeric");
		validator.validate("size", String.valueOf(size));
		
		//user api call with another thread using executor service
		UserApiResponse userApiResponse = userApiExecutorService.getResult(size);
		
		//user list from response
		List<User>  users = Utility.getUserListFromUserApIResponse(userApiResponse);
		
		//format the user dob
		users= Utility.formatDOB(users);
		
		//verifying the nationality and gender of user
		users.forEach(user -> {
			if(nationalityAndGenderAPIExecutorService.checkNationalityAndGender(user)) {
				user.setVerificationStatus(VerificationStatus.VERIFIED);
			}
			else {
				user.setVerificationStatus(VerificationStatus.TO_BE_VERIFIED);
			}
		});
		
		
		//saving the user info into the database
		List<User> savedUsers= userRepo.saveAll(users);
		
		return savedUsers;
	}

	
	@Override
	public UserResponseDTO getUsers(String sortType, String sortOrder, int limit, int offset) {
		
		//validation
		Validator numericValidator= validatorFactory.getValidator("numeric");
		Validator nameValidator = validatorFactory.getValidator("name");
		
		numericValidator.validate("limit",String.valueOf(limit));
		numericValidator.validate("offset",String.valueOf(offset));
		nameValidator.validate("sortType",sortType);
		nameValidator.validate("sortOrder",sortOrder);
		
		List<User> users= new ArrayList<>();
		
		//getting user from database using offset and limit
		for (int i = 0; i < limit; i++) {
			Pageable pageable= PageRequest.of(offset, 1);
			Page<User> page = this.userRepo.findAll(pageable);
			if(page.hasContent()) {
				users.add(page.getContent().get(0));
				offset++;
				if(i==0) {
					userResponseDTO.getPageInfo().setHasPreviousPage(page.hasPrevious());
				}
					userResponseDTO.getPageInfo().setHasNextPage(page.hasNext());
					userResponseDTO.getPageInfo().setTotal(page.getTotalElements());
			}else {
				userResponseDTO.getPageInfo().setHasNextPage(page.hasNext());
				userResponseDTO.getPageInfo().setHasPreviousPage(page.hasPrevious());
				userResponseDTO.getPageInfo().setTotal(page.getTotalElements());
			}
		}
		
		//sorting strategy basis of sortType and sortOrder
		SortStrategy strategy = sortStrategyfactory.getStrategy(sortType, sortOrder);
		
		sortContext.setSortStrategy(strategy);
		
		List<User> sortedUsers = sortContext.sort(users);
		
		userResponseDTO.setUsers(sortedUsers);
		
		return userResponseDTO;
	}

}
