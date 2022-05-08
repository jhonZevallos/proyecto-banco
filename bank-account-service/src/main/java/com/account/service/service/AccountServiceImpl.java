package com.account.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.service.model.Account;
import com.account.service.repository.AccountRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Mono<Account> addAccount(Account a) {

		return accountRepository.save(a);
	}

	@Override
	public Flux<Account> getAllAccount() {

		return accountRepository.findAll();
	}

	@Override
	public Mono<Account> getAccountById(int accountNumber) {

		return accountRepository.findById(accountNumber);
	}

	@Override
	public Mono<Account> getAccountByNroDocumet(int nroDocument) {

		return accountRepository.findByNroDocument(nroDocument);
	}

	@Override
	public Mono<Account> getAccountByTypeAccount(String typeAccount) {

		return accountRepository.findByTypeAccount(typeAccount);
	}

	@Override
	public void deleteCustomer(int accountNumber) {

		accountRepository.deleteById(accountNumber);

	}

}
