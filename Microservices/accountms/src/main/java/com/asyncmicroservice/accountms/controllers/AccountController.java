package com.asyncmicroservice.accountms.controllers;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asyncmicroservice.accountms.entities.Account;
import com.asyncmicroservice.accountms.service.impl.AccountServiceImpl;

/**
 * REST controller for managing accounts
 */
@RestController
@RequestMapping("/cuentas")
public class AccountController {

	// Injecting the AccountService implementation
	@Autowired
    private AccountServiceImpl accountService;

	// Endpoint to retrieve all accounts
    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
    	try {
    		return new ResponseEntity<>(accountService.getAllAccounts().get(), HttpStatus.OK);
    	} catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
    }
    
    // Endpoint to retrieve an account by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable("id") String id) {        
        try {
    		CompletableFuture<Optional<Account>> account = accountService.getAccountById(id);
            if(account.get().isPresent()) {
                return new ResponseEntity<>(account.get().get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	} catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
    }
    
    // Endpoint to retrieve an account by its client ID
    @GetMapping("cliente/{id}")
    public ResponseEntity<?> getAccountsByClientId(@PathVariable("id") String id) {
    	try {
    		return new ResponseEntity<>(accountService.getAllAccountsByClientId(id).get(), HttpStatus.OK);
    	} catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
    }

    // Endpoint to create a new account
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
    	try {
    		CompletableFuture<Account> savedAccount = accountService.createAccount(account);
            return new ResponseEntity<>(savedAccount.get(), HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Data integrity violation: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TransactionSystemException e) {
            return new ResponseEntity<>("Transaction system exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to update an existing account by its ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") String id, @RequestBody Account accountDetails) {
    	try {
    		CompletableFuture<Account> updatedAccount = accountService.updateAccount(id, accountDetails);
            return new ResponseEntity<>(updatedAccount.get(), HttpStatus.OK);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Data integrity violation: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (TransactionSystemException e) {
            return new ResponseEntity<>("Transaction system exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
        	Thread.currentThread().interrupt();
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to delete an account by its ID
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") String id) {
        accountService.deleteAccount(id);
    }
}
