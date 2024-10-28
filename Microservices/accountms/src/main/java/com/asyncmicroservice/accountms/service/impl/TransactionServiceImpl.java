package com.asyncmicroservice.accountms.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.asyncmicroservice.accountms.entities.Account;
import com.asyncmicroservice.accountms.entities.Transaction;
import com.asyncmicroservice.accountms.exceptions.InsufficientBalanceException;
import com.asyncmicroservice.accountms.repositories.AccountRepository;
import com.asyncmicroservice.accountms.repositories.TransactionRepository;
import com.asyncmicroservice.accountms.service.TransactionService;

import jakarta.transaction.Transactional;

/**
 * Implementation of TransactionService
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
	// Injecting the TransactionRepository dependency
    @Autowired
    private TransactionRepository transactionRepository;
    
    // Injecting the AccountRepository dependency
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private KafkaTemplate<String, Transaction> transactionKafkaTemplate;

    private static final String TRANSACTION_TOPIC = "transactions";

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<List<Transaction>> getAllTransactions() {
        return CompletableFuture.completedFuture(transactionRepository.findAll());
    }

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<Optional<Transaction>> getTransactionById(Long id) {
        return CompletableFuture.completedFuture(transactionRepository.findById(id));
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Transaction> createTransaction(Transaction transaction) {
    	if (transaction.getTransactionDate() == null || transaction.getTransactionDate().equals("")) {
            transaction.setTransactionDate(LocalDate.now());
        }
    	
        Account account = accountRepository.findById(transaction.getAccountNumber()).orElseThrow();
        double newBalance = account.getInitialBalance() + transaction.getAmount();
        
        if (newBalance < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }
        
        account.setInitialBalance(newBalance);
        accountRepository.save(account);
        transaction.setBalance(newBalance);
        Transaction savedTransaction = transactionRepository.save(transaction);
        transactionKafkaTemplate.send(TRANSACTION_TOPIC, savedTransaction);
        return CompletableFuture.completedFuture(savedTransaction);
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Transaction> updateTransaction(Long id, Transaction transaction) {
    	Account account = accountRepository.findById(transaction.getAccountNumber()).orElseThrow();
        double newBalance = account.getInitialBalance() + transaction.getAmount();
        
        if (newBalance < 0) {
            throw new InsufficientBalanceException("Saldo no disponible");
        }
        
        account.setInitialBalance(newBalance);
        accountRepository.save(account);
        Transaction updatedTransaction = transactionRepository.findById(id).orElseThrow();
        
        if (transaction.getTransactionDate() == null || transaction.getTransactionDate().equals("")) {
            transaction.setTransactionDate(LocalDate.now());
        }
        
        updatedTransaction.setTransactionDate(transaction.getTransactionDate());
        updatedTransaction.setTransactionType(transaction.getTransactionType());
        updatedTransaction.setAmount(transaction.getAmount());
        updatedTransaction.setBalance(newBalance);
        updatedTransaction.setAccountNumber(transaction.getAccountNumber());
        Transaction savedTransaction = transactionRepository.save(updatedTransaction);
        transactionKafkaTemplate.send(TRANSACTION_TOPIC, savedTransaction);
        return CompletableFuture.completedFuture(savedTransaction);
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
