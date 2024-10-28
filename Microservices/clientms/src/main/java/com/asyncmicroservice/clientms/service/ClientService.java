package com.asyncmicroservice.clientms.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.asyncmicroservice.clientms.entities.Client;

/**
 * Service interface for managing clients
 */
public interface ClientService {
	
	// Retrieve all clients
	CompletableFuture<List<Client>> getAllClients();
	 
	// Retrieve a client by their ID
	CompletableFuture<Optional<Client>> getClientById(String clientId);
	 
	// Create a new client
	CompletableFuture<Client> createClient(Client client);
	 
	// Update an existing client by their identification
	CompletableFuture<Client> updateClient(String identification, Client client);
	 
	// Delete a client by their ID
	void deleteClient(String clientId);
	
	// Generate a client id
	Long generateClientId();
}
