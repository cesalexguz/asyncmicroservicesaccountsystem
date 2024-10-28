package com.asyncmicroservice.clientms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asyncmicroservice.clientms.entities.Person;

/**
 * Repository interface for Person entity
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
}
