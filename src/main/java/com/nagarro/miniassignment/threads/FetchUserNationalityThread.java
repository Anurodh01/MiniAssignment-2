package com.nagarro.miniassignment.threads;

import java.util.concurrent.Callable;

import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.miniassignment.dto.NationalityResponse;

public class FetchUserNationalityThread implements Callable<NationalityResponse> {
	
	private WebClient webClient;
	private String name;
	
	public FetchUserNationalityThread(WebClient client, String name) {
		webClient = client;
		this.name= name;
	}
	
	@Override
	public NationalityResponse call() {
		NationalityResponse response= null;
		try {
		response= webClient.get()
				.uri("/?name={name}", this.name)
				.retrieve()
				.bodyToMono(NationalityResponse.class)
				.block();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return response;
		
	}
	
}
