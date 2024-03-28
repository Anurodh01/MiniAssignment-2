package com.nagarro.miniassignment.config;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {
	
	private final int CONN_TIME_OUT_1= 2000;
	private final int READ_TIME_OUT_1= 2000;
	private final int WRITE_TIME_OUT_1= 2000;
	
	private final int CONN_TIME_OUT_2= 1000;
	private final int READ_TIME_OUT_2= 1000;
	private final int WRITE_TIME_OUT_2= 1000;

	@Bean(name="api1WebClient")
	public WebClient api1WebClient() {
		HttpClient httpClient= getHttpClient(CONN_TIME_OUT_1, READ_TIME_OUT_1, WRITE_TIME_OUT_1);
		
		WebClient webClient = WebClient.builder()
			      .baseUrl("https://randomuser.me")
				  .clientConnector(new ReactorClientHttpConnector(httpClient))
				  .build();
		
		return webClient;
		
	}
	
	@Bean(name="api2WebClient")
	public WebClient api2WebClient() {
		
		HttpClient httpClient= getHttpClient(CONN_TIME_OUT_2, READ_TIME_OUT_2, WRITE_TIME_OUT_2);		
		WebClient webClient = WebClient.builder()
			      .baseUrl("https://api.nationalize.io")
				  .clientConnector(new ReactorClientHttpConnector(httpClient))
				  .build();
		
		return webClient;
	}
	
	@Bean(name="api3WebClient")
	public WebClient api3WebClient() {
		
		HttpClient httpClient= getHttpClient(CONN_TIME_OUT_2, READ_TIME_OUT_2, WRITE_TIME_OUT_2);		
		WebClient webClient = WebClient.builder()
			      .baseUrl("https://api.genderize.io/")
				  .clientConnector(new ReactorClientHttpConnector(httpClient))
				  .build();
		
		return webClient;
	}
	
	public HttpClient getHttpClient(int conn_time_out, int read_time_out, int write_time_out) {
		//http client
		HttpClient httpClient = HttpClient.create()
				  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, conn_time_out)
				  .doOnConnected(conn -> conn
						    .addHandlerLast(new ReadTimeoutHandler(read_time_out, TimeUnit.MILLISECONDS))
						    .addHandlerLast(new WriteTimeoutHandler(write_time_out, TimeUnit.MILLISECONDS)));
		return httpClient;
	}
	
}
