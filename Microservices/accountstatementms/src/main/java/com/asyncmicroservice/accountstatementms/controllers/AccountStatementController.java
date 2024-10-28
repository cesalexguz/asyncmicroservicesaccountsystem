package com.asyncmicroservice.accountstatementms.controllers;

import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asyncmicroservice.accountstatementms.services.AccountStatementService;

/**
 * REST controller for generating account statements
 */
@RestController
@RequestMapping("/reportes")
public class AccountStatementController {

	// Injecting the AccountStatementService implementation
    @Autowired
    private AccountStatementService accountStatementService;

    // Endpoint to generate an account statement for a client within a date range
    @GetMapping
    public ResponseEntity<?> getAccountStatement(
            @RequestParam("clientId") String clientId,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            return new ResponseEntity<>(accountStatementService.getAccountStatement(clientId, startDate, endDate).get(), HttpStatus.OK);
    	} catch (ExecutionException e) {
	        return new ResponseEntity<>("Execution exception: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	    	Thread.currentThread().interrupt();
	        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.BAD_REQUEST);
	    } 
    }
}
