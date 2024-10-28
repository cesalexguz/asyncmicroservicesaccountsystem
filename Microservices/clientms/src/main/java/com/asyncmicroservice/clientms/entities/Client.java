package com.asyncmicroservice.clientms.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity representing a client, inheriting from Person
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="client")
public class Client extends Person {
	
	// Unique client ID
	@Column(name = "client_id", nullable = false, unique = true)
	private Long clientId;
 
	// Password for the client's account
	@NotBlank
	private String password;
	
	@NotNull
	// Status of the client (active/inactive)
	private boolean status;

}
