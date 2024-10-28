package com.asyncmicroservice.accountms.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyncmicroservice.accountms.entities.Transaction;

/**
 * Repository interface for Transaction entity
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
	// Method to find transactions by account number and date range
	List<Transaction> findByAccountNumberAndTransactionDateBetween(String accountNumber, LocalDate startDate, LocalDate endDate);
}
