package com.asyncmicroservice.accountstatementms.services.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.asyncmicroservice.accountstatementms.entities.Account;
import com.asyncmicroservice.accountstatementms.entities.Transaction;

@FeignClient(name = "account-service", url = "http://localhost:8082")
public interface AccountServiceClient {

	@GetMapping("/cuentas/cliente/{clientId}")
    List<Account> getAccountsByClientId(@PathVariable("clientId") String clientId);

    @GetMapping("/movimientos")
    List<Transaction> getAllTransactions();
}
