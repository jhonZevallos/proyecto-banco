package com.credit.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.credit.service.model.Credit;

import reactor.core.publisher.Mono;

public interface CreditRepository extends ReactiveMongoRepository<Credit, Integer> {

	Mono<Credit> findByNroDocument(int nroDocument);

	Mono<Credit> findByTypeCredit(String typeCredit);

}
