package com.asyncmicroservice.clientms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.asyncmicroservice.clientms.entities.Client;
import com.asyncmicroservice.clientms.repositories.ClientRepository;
import com.asyncmicroservice.clientms.service.ClientService;

import jakarta.transaction.Transactional;

/**
 * Implementation of ClientService
 */
@Service
public class ClientServiceImpl implements ClientService {
	
	// Injecting the ClientRepository dependency
    @Autowired
    private ClientRepository clientRepository;
    
    // Injecting the KafkaTemplate dependency
    @Autowired
    private KafkaTemplate<String, Client> kafkaTemplate;
    
    private static final String TOPIC = "clients";

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<List<Client>> getAllClients() {
        return CompletableFuture.completedFuture(clientRepository.findAll());
    }

    @Async("threadPoolExecutor")
    @Override
    public CompletableFuture<Optional<Client>> getClientById(String clientId) {
        return CompletableFuture.completedFuture(clientRepository.findById(clientId));
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Client> createClient(Client client) {
    	client.setClientId(generateClientId());
    	Client savedClient = clientRepository.save(client);
        kafkaTemplate.send(TOPIC, savedClient);
        return CompletableFuture.completedFuture(savedClient);
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public CompletableFuture<Client> updateClient(String identification, Client client) {
    	Client updatedClient = clientRepository.findById(identification).orElseThrow();
    	updatedClient.setName(client.getName());
    	updatedClient.setGender(client.getGender());
    	updatedClient.setAge(client.getAge());
    	updatedClient.setAddress(client.getAddress());
    	updatedClient.setPhone(client.getPhone());
    	updatedClient.setClientId(client.getClientId());
    	updatedClient.setPassword(client.getPassword());
    	updatedClient.setStatus(client.isStatus());
    	Client savedClient = clientRepository.save(updatedClient);
        kafkaTemplate.send(TOPIC, savedClient);
        return CompletableFuture.completedFuture(savedClient);
    }

    @Async("threadPoolExecutor")
    @Transactional
    @Override
    public void deleteClient(String clientId) {
        clientRepository.deleteById(clientId);
    }
    
    @Override
    public Long generateClientId() {
        Long lastClientId = clientRepository.findTopByOrderByClientIdDesc().map(Client::getClientId).orElse(0L);
        return lastClientId + 1;
    }
}
