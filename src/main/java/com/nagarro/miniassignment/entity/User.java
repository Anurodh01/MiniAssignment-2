package com.nagarro.miniassignment.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Builder
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	@Transient
	@JsonIgnore
	private String firstName;
	
	@Transient
	@JsonIgnore
	private String lastName;
	
	private String name;
	private int age;
	private String gender;
	private String dob;
	private String nationality;
	private VerificationStatus verificationStatus;
	
	@JsonIgnore
	private LocalDateTime dateCreated;
	
	@JsonIgnore
	private LocalDateTime dateModified;
	
}
