package com.nagarro.miniassignment.dto;

import java.util.List;

import lombok.Data;

@Data
public class NationalityResponse {
	
	private List<Nationality> country;
	
	@Data
	public static class Nationality{
		private String country_id;
		private String probability;
	}
	
}
