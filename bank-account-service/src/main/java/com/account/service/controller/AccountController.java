package com.account.service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.service.model.Account;
import com.account.service.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<Mono<Account>> createAccount(@RequestBody Account a) {
		a.setMembershipDate(LocalDateTime.now());
		Mono<Account> nuevoAccount = accountService.addAccount(a);
		return ResponseEntity.ok(nuevoAccount);
	}

	@GetMapping("/{accountNumber}")
	public ResponseEntity<Mono<Account>> getAccountById(@PathVariable("accountNumber") int accountNumber) {
		Mono<Account> account = accountService.getAccountById(accountNumber);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(account);
	}

	@GetMapping("/customer/{nroDocument}")
	public ResponseEntity<Mono<Account>> getAccountByNroDocument(@PathVariable("nroDocument") int nroDocument) {
		Mono<Account> account = accountService.getAccountByNroDocumet(nroDocument);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(account);
	}

	@GetMapping
	public ResponseEntity<Flux<Account>> getAllAccount() {
		Flux<Account> account = accountService.getAllAccount();
		return ResponseEntity.ok(account);
	}

	@DeleteMapping("/{accountNumber}")
	public void deleteAccount(@PathVariable("accountNumber") int accountNumber) {
		accountService.deleteCustomer(accountNumber);
	}

}
