package com.asyncmicroservice.accountstatementms.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.asyncmicroservice.accountstatementms.entities.Account;
import com.asyncmicroservice.accountstatementms.entities.AccountStatement;
import com.asyncmicroservice.accountstatementms.entities.Client;
import com.asyncmicroservice.accountstatementms.entities.Transaction;
import com.asyncmicroservice.accountstatementms.services.AccountStatementService;
import com.asyncmicroservice.accountstatementms.services.client.AccountServiceClient;
import com.asyncmicroservice.accountstatementms.services.client.ClientServiceClient;

/**
 * Implementation of AccountStatementService
 */
@Service
public class AccountStatementServiceImpl implements AccountStatementService {
	
	@Autowired
    private ClientServiceClient clientServiceClient;

    @Autowired
    private AccountServiceClient accountServiceClient;

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<AccountStatement> getAccountStatement(String clientId, LocalDate startDate, LocalDate endDate) {
        Client client = clientServiceClient.getClientById(clientId);
        List<Account> accounts = accountServiceClient.getAccountsByClientId(clientId);
        List<Transaction> transactions = accountServiceClient.getAllTransactions().stream()
                .filter(transaction -> transaction.getTransactionDate().isAfter(startDate.minusDays(1)) &&
                        transaction.getTransactionDate().isBefore(endDate.plusDays(1)) &&
                        accounts.stream().anyMatch(account -> account.getAccountNumber().equals(transaction.getAccountNumber())))
                .toList();

        AccountStatement accountStatement = new AccountStatement();
        accountStatement.setClient(client);
        accountStatement.setAccounts(accounts);
        accountStatement.setTransactions(transactions);

        return CompletableFuture.completedFuture(accountStatement);
    }
    
    @KafkaListener(topics = "clients", groupId = "client_group", containerFactory = "clientKafkaListenerContainerFactory")
    public void consumeClient(Client client) {
        System.out.println("Consumed Client: " + client.toString());
    }
    
    @KafkaListener(topics = "accounts", groupId = "account_group", containerFactory = "accountKafkaListenerContainerFactory")
    public void consumeAccount(Account account) {
        System.out.println("Consumed Account: " + account);
    }

    @KafkaListener(topics = "transactions", groupId = "transaction_group", containerFactory = "transactionKafkaListenerContainerFactory")
    public void consumeTransaction(Transaction transaction) {
        System.out.println("Consumed Transaction: " + transaction);
    }
}
