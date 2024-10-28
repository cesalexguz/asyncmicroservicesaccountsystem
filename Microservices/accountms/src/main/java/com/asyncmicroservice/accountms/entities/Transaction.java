package com.asyncmicroservice.accountms.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a transaction
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="transaction")
public class Transaction {
	
	// Unique transaction ID
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 
	// Date of the transaction
	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate transactionDate;
	 
	// Type of the transaction (e.g., deposit, withdrawal)
	@NotBlank
	private String transactionType;
	 
	// Amount involved in the transaction
	@NotNull
	private double amount;
	 
	// Balance after the transaction
	private double balance;
	 
	// Account number associated with the transaction
	@NotBlank
	private String accountNumber;

}
