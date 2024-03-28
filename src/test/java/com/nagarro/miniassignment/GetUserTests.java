package com.nagarro.miniassignment;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nagarro.miniassignment.controller.UserController;
import com.nagarro.miniassignment.dto.UserResponseDTO;
import com.nagarro.miniassignment.entity.User;
import com.nagarro.miniassignment.entity.VerificationStatus;
import com.nagarro.miniassignment.exception.GlobalExceptionHandler;
import com.nagarro.miniassignment.service.UserService;

@SpringBootTest
public class GetUserTests {
	
	private MockMvc mockMvc;
	
	
	@Autowired
	private UserResponseDTO userResponseDTO;

	@Mock
	private UserService userService;
	
	
	@InjectMocks
	private UserController userController;

	
	@BeforeEach
	private void setUp() {
		this.mockMvc= MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
		List<User> users= new ArrayList<>();
		User user1= User.builder().name("Anurodh Singh").firstName("Anurodh").lastName("Singh").age(21).dob("17 Aug 2002").gender("male").nationality("IN").verificationStatus(VerificationStatus.TO_BE_VERIFIED).dateCreated(LocalDateTime.now()).dateModified(LocalDateTime.now()).build();
		User user3= User.builder().name("Dummy Data").firstName("Dummy").lastName("Data").age(33).dob("22 Apr 1990").gender("male").nationality("IN").verificationStatus(VerificationStatus.TO_BE_VERIFIED).dateCreated(LocalDateTime.now()).dateModified(LocalDateTime.now()).build();
		User user2= User.builder().name("Michael Jordan").firstName("Michael").lastName("Jordan").age(37).dob("11 Sep 1986").gender("male").nationality("GR").verificationStatus(VerificationStatus.VERIFIED).dateCreated(LocalDateTime.now()).dateModified(LocalDateTime.now()).build();
		
		users.add(user1); users.add(user2); users.add(user3);
		userResponseDTO.getPageInfo().setTotal(users.size());;
		userResponseDTO.setUsers(users);
	}
	
	@Test
	public void getUserTest_success() throws Exception {
		
		Mockito.when(userService.getUsers(anyString(), anyString(), anyInt(), anyInt())).thenReturn(userResponseDTO);
		
		this.mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("sortType", "age")
				.queryParam("sortOrder", "odd")
				.queryParam("limit", "2")
				.queryParam("offset", "0"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.users.length()", is(3)))
				.andExpect(jsonPath("$.users[0].dob", is("17 Aug 2002")));
					
	}
	
	@Test
	public void getUserTest_EnglishAlphabetValidation() throws Exception {
		
		when(userService.getUsers(anyString(), anyString(), anyInt(), anyInt())).thenThrow(new IllegalArgumentException());
		
		mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("sortType", "f3g2")
				.queryParam("sortOrder", "odd")
				.queryParam("limit", "2")
				.queryParam("offset", "0"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserTest_NumericValidator() throws Exception {
		
		when(userService.getUsers(anyString(), anyString(), anyInt(), anyInt())).thenThrow(new IllegalArgumentException());
		
		mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("sortType", "age")
				.queryParam("sortOrder", "even")
				.queryParam("limit", "limit")
				.queryParam("offset", "4"))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void getUserTest_InvalidParameterNumber() throws Exception{
		
		when(userService.getUsers(anyString(), anyString(), anyInt(), anyInt())).thenThrow(new IllegalArgumentException());
		
		mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("sortType", "age")
				.queryParam("sortOrder", "even")
				.queryParam("offset", "2"))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void getUserTest_InvalidLimit() throws Exception{
		
		when(userService.getUsers(anyString(), anyString(), anyInt(), anyInt())).thenThrow(new IllegalArgumentException());
		
		mockMvc.perform(get("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("sortType", "age")
				.queryParam("sortOrder", "odd")
				.queryParam("limit","10")
				.queryParam("offset", "5"))
				.andExpect(status().isBadRequest());
		
	}
	
	
}
