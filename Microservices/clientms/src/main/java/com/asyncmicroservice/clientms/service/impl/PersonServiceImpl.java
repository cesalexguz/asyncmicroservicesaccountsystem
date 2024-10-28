package com.asyncmicroservice.clientms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.asyncmicroservice.clientms.entities.Person;
import com.asyncmicroservice.clientms.repositories.PersonRepository;
import com.asyncmicroservice.clientms.service.PersonService;

import jakarta.transaction.Transactional;

/**
 * Implementation of PersonService
 */
@Service
public class PersonServiceImpl implements PersonService {
	
	// Injecting the PersonRepository dependency
    @Autowired
    private PersonRepository personRepository;

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<List<Person>> getAllPersons() {
        return CompletableFuture.completedFuture(personRepository.findAll());
    }

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<Optional<Person>> getPersonById(String identification) {
        return CompletableFuture.completedFuture(personRepository.findById(identification));
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Person> createPerson(Person person) {
        return CompletableFuture.completedFuture(personRepository.save(person));
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Person> updatePerson(String identification, Person person) {
    	Person updatedPerson = personRepository.findById(identification).orElseThrow();
    	updatedPerson.setName(person.getName());
    	updatedPerson.setGender(person.getGender());
    	updatedPerson.setAge(person.getAge());
    	updatedPerson.setAddress(person.getAddress());
    	updatedPerson.setPhone(person.getPhone());
    	return CompletableFuture.completedFuture(personRepository.save(updatedPerson));
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public void deletePerson(String identification) {
        personRepository.deleteById(identification);
    }
}
