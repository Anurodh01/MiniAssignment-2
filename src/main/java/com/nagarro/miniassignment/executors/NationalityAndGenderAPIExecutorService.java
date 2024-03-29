package com.nagarro.miniassignment.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.miniassignment.dto.GenderApiResponse;
import com.nagarro.miniassignment.dto.NationalityResponse;
import com.nagarro.miniassignment.entity.User;
import com.nagarro.miniassignment.threads.FetchUserGenderThread;
import com.nagarro.miniassignment.threads.FetchUserNationalityThread;

@Component
public class NationalityAndGenderAPIExecutorService {
	
	@Autowired
	@Qualifier("api2WebClient")
	private WebClient api2WebClient;
	
	@Autowired
	@Qualifier("api3WebClient")
	private WebClient api3WebClient;
	
	public boolean checkNationalityAndGender(User user) {
		
		//creating the executor service 
		ExecutorService service= Executors.newFixedThreadPool(2);
		
		Future<NationalityResponse> result1= service.submit(new FetchUserNationalityThread(api2WebClient, user.getFirstName()));
		Future<GenderApiResponse> result2= service.submit(new FetchUserGenderThread(api3WebClient, user.getFirstName()));		
		
		GenderApiResponse genderApiResponse = null;
		NationalityResponse nationalityResponse = null;
		
		try {
			nationalityResponse= result1.get();
			genderApiResponse= result2.get();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		boolean isNationalityMatch = (nationalityResponse != null) && nationalityResponse.getCountry()
					.stream().anyMatch(country -> country.getCountry_id().equals(user.getNationality()));

		boolean isGenderMatch= false;
		if((genderApiResponse!= null ) && genderApiResponse.getGender()!=null && genderApiResponse.getGender().equalsIgnoreCase(user.getGender())) {
			isGenderMatch= true;
		}


		service.shutdown();
		
		return isNationalityMatch && isGenderMatch;	
	}
	
}
