package com.asyncmicroservice.accountms.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.asyncmicroservice.accountms.entities.Account;

/**
 * Service interface for managing accounts
 */
public interface AccountService {
	
	// Retrieve all accounts
	CompletableFuture<List<Account>> getAllAccounts();
	
	// Retrieve all accounts using client id
	CompletableFuture<List<Account>> getAllAccountsByClientId(String clientId);
	
	// Retrieve an account by its number
	CompletableFuture<Optional<Account>> getAccountById(String accountNumber);
	 
	// Create a new account
	CompletableFuture<Account> createAccount(Account account);
	 
	// Update an existing account by its number
	CompletableFuture<Account> updateAccount(String accountNumber, Account account);
	 
	// Delete an account by its number
	void deleteAccount(String accountNumber);
	
}
