package com.transaction.service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.service.model.Transaction;
import com.transaction.service.service.TransactionService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<Mono<Transaction>> createTransaction(@RequestBody Transaction t) {
		t.setDateTransaction(LocalDateTime.now());
		Mono<Transaction> nuevaTransaction = transactionService.addTransaction(t);
		return ResponseEntity.ok(nuevaTransaction);
	}

	@GetMapping("{idTransaction}")
	public ResponseEntity<Mono<Transaction>> getTransactionById(@PathVariable("idTransaction") int idTransaction) {
		Mono<Transaction> transaction = transactionService.getTransactionById(idTransaction);
		if (transaction == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(transaction);
	}

	@GetMapping("/{nroDocument}")
	public ResponseEntity<Mono<Transaction>> getTransactionByNroDocument(@PathVariable("nroDocument") int nroDocument) {
		Mono<Transaction> transaction = transactionService.getTransactionByNroDoc(nroDocument);
		if (transaction == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(transaction);
	}

	@GetMapping
	public ResponseEntity<Flux<Transaction>> getAllTransaction() {
		Flux<Transaction> transaction = transactionService.getAllTransaction();
		return ResponseEntity.ok(transaction);
	}

}
