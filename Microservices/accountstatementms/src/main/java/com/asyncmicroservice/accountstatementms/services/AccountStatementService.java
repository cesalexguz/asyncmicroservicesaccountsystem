package com.asyncmicroservice.accountstatementms.services;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import com.asyncmicroservice.accountstatementms.entities.AccountStatement;

/**
 * Service interface for generating account statements
 */
public interface AccountStatementService {
	
	// Generate an account statement for a client within a date range
	CompletableFuture<AccountStatement> getAccountStatement(String clientId, LocalDate startDate, LocalDate endDate);
	
}