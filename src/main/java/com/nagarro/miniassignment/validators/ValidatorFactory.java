package com.nagarro.miniassignment.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidatorFactory {
	
	@Autowired
	private NumericValidator numericValidator;
	
	@Autowired
	private EngLishAlphabetValidator engLishAlphabetValidator;
	
	public Validator getValidator(String validatorType) {
		if(validatorType.equalsIgnoreCase("numeric")) {
			return numericValidator;
		}else if(validatorType.equalsIgnoreCase("name")){
			return engLishAlphabetValidator;
		}else {
			throw new IllegalArgumentException("Invalid parameter Type");
		}
	}
	
}
