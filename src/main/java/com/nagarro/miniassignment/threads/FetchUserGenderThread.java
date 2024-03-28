package com.nagarro.miniassignment.threads;

import java.util.concurrent.Callable;

import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.miniassignment.dto.GenderApiResponse;

public class FetchUserGenderThread implements Callable<GenderApiResponse> {
	
	private WebClient webClient;
	private String name;
	
	public FetchUserGenderThread(WebClient client, String name) {
		webClient = client;
		this.name= name;
	}
	
	@Override
	public GenderApiResponse call() {
		GenderApiResponse response=null;
		
		try {
		response= webClient.get()
				.uri("/?name={name}", this.name)
				.retrieve()
				.bodyToMono(GenderApiResponse.class)
				.block();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
	
	
}
