package com.asyncmicroservice.accountstatementms.entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Base model class for all transaction-related entities
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class Transaction {
	
	// Unique transaction ID
	private Long id;
	 
	// Date of the transaction
	private LocalDate transactionDate;
	 
	// Type of the transaction (e.g., deposit, withdrawal)
	private String transactionType;
	 
	// Amount involved in the transaction
	private double amount;
	 
	// Balance after the transaction
	private double balance;
	 
	// Account number associated with the transaction
	private String accountNumber;
}
