package com.asyncmicroservice.accountstatementms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base model class for all person-related entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Person {
	
	// Unique identification for the person
	private String identification;
	 
	// Name of the person
	private String name;
	 
	// Gender of the person
	private String gender;
	 
	// Age of the person
	private int age;
	 
	// Address of the person
	private String address;
	 
	// Phone number of the person
	private String phone;
}