package com.asyncmicroservice.clientms.entities;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Base class for all person-related entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Person {
	
	// Unique identification for the person
	@Id
	private String identification;
	 
	// Name of the person
	@NotBlank
	private String name;
	 
	// Gender of the person
	@NotBlank
	private String gender;
	 
	// Age of the person
	@NotNull
	private int age;
	 
	// Address of the person
	@NotBlank
	private String address;
	 
	// Phone number of the person
	@NotBlank
	private String phone;
	
}