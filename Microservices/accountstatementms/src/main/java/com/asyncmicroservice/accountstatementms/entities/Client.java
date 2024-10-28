package com.asyncmicroservice.accountstatementms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Model class representing a client, inheriting from Person
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Client extends Person {
	
	// Unique client ID
	private Long clientId;
 
	// Password for the client's account
	private String password;
	 
	// Status of the client (active/inactive)
	private boolean status;
}
