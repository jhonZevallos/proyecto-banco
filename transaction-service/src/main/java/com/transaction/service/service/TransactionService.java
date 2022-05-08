package com.transaction.service.service;

import com.transaction.service.model.Transaction;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionService {

	Mono<Transaction> addTransaction(Transaction t);

	Flux<Transaction> getAllTransaction();

	Mono<Transaction> getTransactionById(int idTransaction);

	Mono<Transaction> getTransactionByNroDoc(int nroDocument);

	Mono<Transaction> getTransactionByTypeTransaction(String typeTransaction);
}
