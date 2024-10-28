package com.asyncmicroservice.clientms.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.asyncmicroservice.clientms.entities.Person;

/**
 * Service interface for managing persons
 */
public interface PersonService {
	
	// Retrieve all persons
	CompletableFuture<List<Person>> getAllPersons();
	 
	// Retrieve a person by their identification
	CompletableFuture<Optional<Person>> getPersonById(String identification);
	 
	// Create a new person
	CompletableFuture<Person> createPerson(Person person);
	 
	// Update an existing person by their identification
	CompletableFuture<Person> updatePerson(String identification, Person person);
	 
	// Delete a person by their identification
	void deletePerson(String identification);
	
}
