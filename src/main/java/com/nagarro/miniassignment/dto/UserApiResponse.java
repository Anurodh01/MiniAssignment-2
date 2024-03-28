package com.nagarro.miniassignment.dto;

import java.util.List;

import lombok.Data;


@Data
public class UserApiResponse {
	
	private List<Result> results;
	
	@Data
	public static class Result{
		private String gender;
		private Name name;
		private Dob dob;
		private String nat;
		
		@Data
		public static class Name{
			private String title;
			private String first;
			private String last;
		}
				
		@Data
		public static class Dob{
			private String date;
			private int age;
		}	

	}
	
}
