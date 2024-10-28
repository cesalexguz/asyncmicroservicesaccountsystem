package com.asyncmicroservice.accountms.exceptions;

/**
 * Custom exception to indicate insufficient balance in an account
 */
public class InsufficientBalanceException extends RuntimeException {
		
	// Unique identifier for serialization
	private static final long serialVersionUID = -8914259964406794888L;
	
	// Constructor that accepts a custom error message
	public InsufficientBalanceException(String message) {
		super(message);
	}
}
