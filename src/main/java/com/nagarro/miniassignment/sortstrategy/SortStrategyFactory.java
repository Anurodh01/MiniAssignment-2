package com.nagarro.miniassignment.sortstrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortStrategyFactory {
	
	@Autowired
	private AgeEvenFirstStrategy ageEvenFirstStrategy;
	
	@Autowired
	private NameOddFirstStrategy nameOddFirstStrategy;
	
	@Autowired
	private AgeOddFirstStrategy ageOddFirstStrategy;
	
	@Autowired
	private NameEvenFirstStrategy nameEvenFirstStrategy;
	
	public SortStrategy getStrategy(String sortType, String sortOrder) {
		
		if(sortType.equalsIgnoreCase("Age") && sortOrder.equalsIgnoreCase("Even")) {
			return ageEvenFirstStrategy;
		}else if(sortType.equalsIgnoreCase("Name") && sortOrder.equalsIgnoreCase("Odd")) {
			return nameOddFirstStrategy;
		}else if(sortType.equalsIgnoreCase("Age") && sortOrder.equalsIgnoreCase("Odd")) {
			return ageOddFirstStrategy;
		}else if (sortType.equalsIgnoreCase("Name") && sortOrder.equalsIgnoreCase("Even")) {
			return nameEvenFirstStrategy;
		}else {
			throw new IllegalArgumentException("Illegal Argument Passed");
		}
		
		
	}
	
}
