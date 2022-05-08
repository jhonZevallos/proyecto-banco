package com.customer.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.customer.service.model.Customer;

import reactor.core.publisher.Mono;

public interface CustomerRepository extends ReactiveMongoRepository<Customer, Integer>{
	
	Mono<Customer> findByNroDocument(int nroDocument);
	
	Mono<Customer> findByTypeCustomer(String typeCustomer);

}
