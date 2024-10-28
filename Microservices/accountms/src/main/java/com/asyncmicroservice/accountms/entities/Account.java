package com.asyncmicroservice.accountms.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a bank account
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="account")
public class Account {
	
	// Unique account number
	@Id
	private String accountNumber;
	 
	// Type of the account (e.g., savings, checking)
	@NotBlank
	private String accountType;
	 
	// Initial balance of the account
	@NotNull
	private double initialBalance;
	 
	// Status of the account (active/inactive)
	private boolean status;
	 
	// ID of the client who owns the account
	@NotBlank
	private String clientId;

}