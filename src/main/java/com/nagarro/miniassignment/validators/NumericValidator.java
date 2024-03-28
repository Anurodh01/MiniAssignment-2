package com.nagarro.miniassignment.validators;

import org.springframework.stereotype.Component;

@Component
public class NumericValidator implements Validator{
	
	private NumericValidator() {
	}
	
	@Override
	public boolean validate(String field, String value) {
		
		try {
			int numberValue= Integer.parseInt(value);
			if(field.equals("limit") || field.equals("size")) {
				if(numberValue < 1 || numberValue > 5) {
					throw new IllegalArgumentException(field+" should be in between 1 and 5");
				}
			}
		} catch (NumberFormatException e) {
			throw new NumberFormatException(field+": "+"Integer value is expected");
		}
		return true;
	}
	
}