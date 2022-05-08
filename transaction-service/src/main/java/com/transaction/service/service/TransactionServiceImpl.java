package com.transaction.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transaction.service.model.Transaction;
import com.transaction.service.repository.TransactionRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Mono<Transaction> addTransaction(Transaction t) {

		return transactionRepository.save(t);
	}

	@Override
	public Flux<Transaction> getAllTransaction() {

		return transactionRepository.findAll();
	}

	@Override
	public Mono<Transaction> getTransactionById(int idTransaction) {

		return transactionRepository.findById(idTransaction);
	}

	@Override
	public Mono<Transaction> getTransactionByNroDoc(int nroDocument) {

		return transactionRepository.findByNroDocument(nroDocument);
	}

	@Override
	public Mono<Transaction> getTransactionByTypeTransaction(String typeTransaction) {

		return transactionRepository.findByTypeTransaction(typeTransaction);
	}

}
