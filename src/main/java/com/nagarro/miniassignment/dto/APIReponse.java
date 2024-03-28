package com.nagarro.miniassignment.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class APIReponse {
	
	public APIReponse(String message, int code, Date timeStamp) {
		this.message= message;
		this.code = code;
		
		SimpleDateFormat dateFormat= new SimpleDateFormat("E, MMM dd yyyy hh:mm:ss");
		String date= dateFormat.format(timeStamp);
		
		this.timeStamp= date;
	}
	
	private String message;
	private int code;
	private String timeStamp;
	
}
