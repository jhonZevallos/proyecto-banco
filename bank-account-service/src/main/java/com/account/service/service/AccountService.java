package com.account.service.service;

import com.account.service.model.Account;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AccountService {

	Mono<Account> addAccount(Account a);

	Flux<Account> getAllAccount();

	Mono<Account> getAccountById(int accountNumber);

	Mono<Account> getAccountByNroDocumet(int nroDocument);

	Mono<Account> getAccountByTypeAccount(String typeAccount);
	
	void deleteCustomer(int accountNumber);
}
