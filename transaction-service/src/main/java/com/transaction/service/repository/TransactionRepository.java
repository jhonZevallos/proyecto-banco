package com.transaction.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.transaction.service.model.Transaction;

import reactor.core.publisher.Mono;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, Integer> {

	Mono<Transaction> findByNroDocument(int nroDocument);

	Mono<Transaction> findByTypeTransaction(String typeTransaction);
}
