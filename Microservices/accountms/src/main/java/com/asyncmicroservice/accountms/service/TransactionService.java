package com.asyncmicroservice.accountms.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.asyncmicroservice.accountms.entities.Transaction;

/**
 * Service interface for managing transactions
 */
public interface TransactionService {
	
	// Retrieve all transactions
	CompletableFuture<List<Transaction>> getAllTransactions();
	 
	// Retrieve a transaction by its ID
	CompletableFuture<Optional<Transaction>> getTransactionById(Long id);
	 
	// Create a new transaction
	CompletableFuture<Transaction> createTransaction(Transaction transaction);
	 
	// Update an existing transaction by its ID
	CompletableFuture<Transaction> updateTransaction(Long id, Transaction transaction);
	 
	// Delete a transaction by its ID
	void deleteTransaction(Long id);

}
