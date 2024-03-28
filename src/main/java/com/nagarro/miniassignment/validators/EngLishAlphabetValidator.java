package com.nagarro.miniassignment.validators;

import org.springframework.stereotype.Component;

@Component
public class EngLishAlphabetValidator implements Validator {
	
	private EngLishAlphabetValidator() {}
	
	@Override
	public boolean validate(String field, String value) {
		
		if(!value.matches("[a-zA-z]+")) {
			throw new IllegalArgumentException("For "+field+"("+value+")"+" only english alphabetic input expected!!");
		}
		
		if(field.equals("sortType")) {
			if(!value.equalsIgnoreCase("name") && !value.equalsIgnoreCase("Age")) {
				throw new IllegalArgumentException("Allowed values for sortType is [Name/Age] only!!");
			}
		}
		
		if(field.equals("sortOrder")) {
			if(!value.equalsIgnoreCase("EVEN") && !value.equalsIgnoreCase("ODD")) {
				throw new IllegalArgumentException("Allowed values for sortOrder is [EVEN/ODD] only");
			}
		}
		
		return true;
	}

}
