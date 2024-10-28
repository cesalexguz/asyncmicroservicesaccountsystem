package com.asyncmicroservice.accountms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.asyncmicroservice.accountms.entities.Account;
import com.asyncmicroservice.accountms.repositories.AccountRepository;
import com.asyncmicroservice.accountms.service.AccountService;

import jakarta.transaction.Transactional;

/**
 * Implementation of AccountService
 */
@Service
public class AccountServiceImpl implements AccountService {
	
	// Injecting the AccountRepository dependency
    @Autowired
    private AccountRepository accountRepository;
    
    // Injecting the KafkaTemplate dependency
    @Autowired
    private KafkaTemplate<String, Account> accountKafkaTemplate;

    private static final String ACCOUNT_TOPIC = "accounts";
    
    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<List<Account>> getAllAccounts() {
        return CompletableFuture.completedFuture(accountRepository.findAll());
    }
    
    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<List<Account>> getAllAccountsByClientId(String clientId) {
        return CompletableFuture.completedFuture(accountRepository.findByClientId(clientId));
    }

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<Optional<Account>> getAccountById(String accountNumber) {
        return CompletableFuture.completedFuture(accountRepository.findById(accountNumber));
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Account> createAccount(Account account) {
    	Account savedAccount = accountRepository.save(account);
        accountKafkaTemplate.send(ACCOUNT_TOPIC, savedAccount);
        return CompletableFuture.completedFuture(savedAccount);
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Account> updateAccount(String accountNumber, Account account) {
    	Account updatedAccount = accountRepository.findById(accountNumber).orElseThrow();
        updatedAccount.setAccountNumber(account.getAccountNumber());
        updatedAccount.setAccountType(account.getAccountType());
        updatedAccount.setInitialBalance(account.getInitialBalance());
        updatedAccount.setStatus(account.isStatus());
        updatedAccount.setClientId(account.getClientId());
        Account savedAccount = accountRepository.save(updatedAccount);
        accountKafkaTemplate.send(ACCOUNT_TOPIC, savedAccount);
        return CompletableFuture.completedFuture(savedAccount);
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public void deleteAccount(String accountNumber) {
        accountRepository.deleteById(accountNumber);
    }
}
