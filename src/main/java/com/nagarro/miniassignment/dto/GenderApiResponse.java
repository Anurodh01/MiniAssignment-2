package com.nagarro.miniassignment.dto;

import lombok.Data;

@Data
public class GenderApiResponse {

	private String name;
	private String gender;
	private String probability;
	private int count;
		
}
