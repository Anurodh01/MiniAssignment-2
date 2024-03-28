package com.nagarro.miniassignment.threads;

//import java.net.http.HttpTimeoutException;
import java.util.concurrent.Callable;

import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.miniassignment.dto.UserApiResponse;

import io.netty.handler.timeout.ReadTimeoutException;

public class FetchUserThread implements Callable<UserApiResponse> {
	
	private WebClient webClient;
	private int size;
	
	public FetchUserThread(WebClient client, int size) {
		webClient = client;
		this.size= size;
	}
	
	@Override
	public UserApiResponse call() {
		UserApiResponse apiResponse= null;
		try {
		
		 apiResponse = webClient.get()
				.uri("/api/?results={size}", size)
				.retrieve()
				.bodyToMono(UserApiResponse.class)
				.retry(2)
//				.onErrorMap(ReadTimeoutException.class, ex -> new HttpTimeoutException("ReadTimeout"))
				.block();
		}catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		return apiResponse;
				
		
	}
	
}
