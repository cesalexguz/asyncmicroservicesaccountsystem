package com.asyncmicroservice.accountstatementms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Model class representing a bank account
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Account {
	
	// Unique account number
	private String accountNumber;
	 
	// Type of the account (e.g., savings, checking)
	private String accountType;
	 
	// Initial balance of the account
	private double initialBalance;
	 
	// Status of the account (active/inactive)
	private boolean status;
	 
	// ID of the client who owns the account
	private String clientId;
}