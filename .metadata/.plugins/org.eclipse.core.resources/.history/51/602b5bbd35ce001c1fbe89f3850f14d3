package com.account.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.account.service.model.Account;

import reactor.core.publisher.Mono;

public interface AccountRepository extends ReactiveMongoRepository<Account, Integer> {

	Mono<Account> findByNroDocument(int nroDocument);

	Mono<Account> findByTypeAccount(String typeAccount);
}
