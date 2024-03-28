package com.nagarro.miniassignment.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.miniassignment.dto.UserApiResponse;
import com.nagarro.miniassignment.threads.FetchUserThread;


@Component
public class UserApiExecutorService {
	
	@Autowired
	@Qualifier("api1WebClient")
	private WebClient api1WebClient;
	
	public UserApiResponse getResult(int size) {
		
		//user api call with another thread using executor service
		ExecutorService service= Executors.newCachedThreadPool();
		Future<UserApiResponse> result = service.submit(new FetchUserThread(api1WebClient, size));
				
		UserApiResponse userApiResponse= null;
				
		try {
				userApiResponse= result.get();
		} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
		}finally{
					service.shutdown();
			}
		
		return userApiResponse;
	}
	
}
