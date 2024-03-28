package com.nagarro.miniassignment;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.miniassignment.controller.UserController;
import com.nagarro.miniassignment.entity.User;
import com.nagarro.miniassignment.entity.VerificationStatus;
import com.nagarro.miniassignment.exception.GlobalExceptionHandler;
import com.nagarro.miniassignment.service.UserService;

@SpringBootTest
class CreateUserTests {
	
	
	private MockMvc mockMvc;
	private User user;

	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserController userController;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	private void setUp() {
		this.mockMvc= MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
		this.user = User.builder().age(20).dob("27 May 2002").firstName("anurodh").lastName("singh").name("anurodh singh").gender("male").verificationStatus(VerificationStatus.VERIFIED).build();
	}
	
	@Test
	public void createUserTest_success() throws Exception {
		
		List<User> users = new ArrayList<>();
		users.add(user);
		Mockito.when(userService.createUser(anyInt())).thenReturn(users);

		mockMvc.perform(post("/users")
				.queryParam("size", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$").exists())
				.andExpect(jsonPath("$.length()", is(1)))
				.andExpect(jsonPath("$[0].verificationStatus", is("VERIFIED")))
				.andExpect(jsonPath("$[0].name", is("anurodh singh")));		

	}
	
	@Test
	public void createUserTest_invalidInputSize() throws Exception {
		
		Mockito.when(userService.createUser(anyInt())).thenThrow(new IllegalArgumentException("size should be in between 1 and 5"));
		
		mockMvc.perform(post("/users")
				.queryParam("size", "10")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("size should be in between 1 and 5")));
		
	}
	
	@Test
	public void createUserTest_InvalidInputType() throws Exception {
		
		Mockito.when(userService.createUser(anyInt())).thenThrow(new IllegalArgumentException("For \"four\" int type value is expected!!"));
		
		mockMvc.perform(post("/users")
				.queryParam("size", "four")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("For \"four\" int type value is expected!!")));
	
	}
	
	@Test
	public void createUserTest_invalidParams() throws Exception {
		
		mockMvc.perform(post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.message", is("Required request parameter 'size' for method parameter type int is not present")));
	}
	
	@Test
	public void createUserTest_InvalidUrl() throws Exception {
		
		mockMvc.perform(post("/ussers")
				.queryParam("size", "1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("No URL mapping found!!")));
		
	}
	
	

}
