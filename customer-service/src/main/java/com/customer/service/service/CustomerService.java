package com.customer.service.service;

import com.customer.service.client.Account;
import com.customer.service.client.Credit;
import com.customer.service.client.Report;
import com.customer.service.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

	Mono<Customer> addCustomer(Customer c);

	Mono<Customer> updateCustomer(Customer c);

	Flux<Customer> getAllCustomer();

	Mono<Customer> getCustomerById(int id);

	Mono<Customer> getCustomerByNroDoc(int nroDocument);

	Mono<Customer> getCustomerByTypeCustomer(String typeCustomer);

	void deleteCustomer(int id);

	// ******metodos feing********************
	Mono<Account> getAccountByNroDoc(int nroDocument);

	Mono<Account> addAccount(int nroDocument, Account a);
	
	Mono<Credit> getCreditByNroDoc(int nroDocument);
	
	Mono<Credit> addCredit(int nroDocument,Credit credit);
	
	Mono<Report> getCustomerAndProducts(int nroDocument);
}
