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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asyncmicroservice.accountms.entities.Transaction;
import com.asyncmicroservice.accountms.exceptions.InsufficientBalanceException;
import com.asyncmicroservice.accountms.service.impl.TransactionServiceImpl;

/**
 * REST controller for managing transactions
 */
@RestController
@RequestMapping("/movimientos")
public class TransactionController {

	// Injecting the TransactionService implementation
	@Autowired
    private TransactionServiceImpl transactionService;

	// Endpoint to retrieve all transactions
    @GetMapping
    public ResponseEntity<?> getAllTransactions() {    	
    	try {
    		return new ResponseEntity<>(transactionService.getAllTransactions().get(), HttpStatus.OK);
    	} catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
    }
    
    // Endpoint to retrieve a transaction by its ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable("id") Long id) {
    	try {
    		CompletableFuture<Optional<Transaction>> transaction = transactionService.getTransactionById(id);
            if(transaction.get().isPresent()) {
                return new ResponseEntity<>(transaction.get().get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	} catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
    }
    
    // Endpoint to create a new transaction
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody Transaction Transaction) {
    	try {
    		CompletableFuture<Transaction> savedTransaction = transactionService.createTransaction(Transaction);
            return new ResponseEntity<>(savedTransaction.get(), HttpStatus.OK);

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

    // Endpoint to update an existing transaction by its ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable("id") Long id, @RequestBody Transaction TransactionDetails) {
    	try {
    		CompletableFuture<Transaction> updatedTransaction = transactionService.updateTransaction(id, TransactionDetails);
            return new ResponseEntity<>(updatedTransaction.get(), HttpStatus.OK);

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

    // Endpoint to delete a transaction by its ID
    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable("id") Long id) {
        transactionService.deleteTransaction(id);
    }
    
    // Exception handler for InsufficientBalanceException
    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<String> handleInsufficientBalanceException(InsufficientBalanceException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    
}
