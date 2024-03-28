package com.nagarro.miniassignment.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class PageInfo {
	private boolean hasNextPage;
	private boolean hasPreviousPage;
	private long total;
}
