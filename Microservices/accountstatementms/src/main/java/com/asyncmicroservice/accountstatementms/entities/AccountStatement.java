package com.asyncmicroservice.accountstatementms.entities;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Model class representing an account statement
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AccountStatement {
	
	// Client associated with the account statement
	private Client client;
	 
	// List of accounts associated with the client
	private List<Account> accounts;
	 
	// List of transactions associated with the accounts
	private List<Transaction> transactions;

}