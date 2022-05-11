package com.customer.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.customer.service.client.Account;
import com.customer.service.client.Credit;
import com.customer.service.client.Report;
import com.customer.service.model.Customer;
import com.customer.service.repository.CustomerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

	@Override
	public Mono<Customer> addCustomer(Customer c) {

		return customerRepository.save(c);
	}

	@Override
	public Mono<Customer> updateCustomer(Customer c) {
		int idCustomer = c.getId();
		if (idCustomer < 0) {
			c.setId(idCustomer);
		}
		return customerRepository.save(c);
	}

	@Override
	public Flux<Customer> getAllCustomer() {

		return customerRepository.findAll();
	}

	@Override
	public Mono<Customer> getCustomerById(int id) {

		return customerRepository.findById(id);
	}

	@Override
	public Mono<Customer> getCustomerByNroDoc(int nroDocument) {

		return customerRepository.findByNroDocument(nroDocument);
	}

	@Override
	public void deleteCustomer(int id) {
		customerRepository.deleteById(id);

	}

	@Override
	public Mono<Customer> getCustomerByTypeCustomer(String typeCustomer) {

		return customerRepository.findByTypeCustomer(typeCustomer);
	}

	// ************************************METODOS
	// WebClient*****************************************

	@Override
	public Mono<Account> getAccountByNroDoc(int nroDocument) {
		Mono<Account> account = WebClient.create("http://localhost:8080").get().uri("/account/customer/" + nroDocument)
				.retrieve()
				.bodyToMono(Account.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty()); 
				}) ;
		return account;
	}

	@Override
	public Mono<Account> addAccount(int nroDocument, Account a) {
		a.setNroDocument(nroDocument);
		Mono<Account> nuevoAccount = WebClient.create("http://localhost:8080").post().uri("/account")
				.body(Mono.just(a), Account.class)
				.retrieve()
				.bodyToMono(Account.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty()); 
				});
		return nuevoAccount;
	}

	@Override
	public Mono<Credit> getCreditByNroDoc(int nroDocument) {
		Mono<Credit> credit = WebClient.create("http://localhost:8080").get().uri("/credit/customer/" + nroDocument)
				.retrieve()
				.bodyToMono(Credit.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty()); 
				}) ;
		return credit;
	}

	@Override
	public Mono<Credit> addCredit(int nroDocument, Credit credit) {
		credit.setNroDocument(nroDocument);
		Mono<Credit> nuevoCredit = WebClient.create("http://localhost:8080").post().uri("/credit")
				.body(Mono.just(credit), Credit.class)
				.retrieve()
				.bodyToMono(Credit.class)
				.transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty()); 
				}) ;
		return nuevoCredit;
	}

	@Override
	public Mono<Report> getCustomerAndProducts(int nroDocument) {
		Report report = new Report();

		return customerRepository.findByNroDocument(nroDocument)
				.defaultIfEmpty(new Customer())
				.flatMap(customer->{
					return WebClient.create("http://localhost:8080").get().uri("/account/customer/{nroDocument}", nroDocument)
							.retrieve().bodyToMono(Account.class)
							.transformDeferred(it -> {
								ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
								return rcb.run(it, throwable -> Mono.empty()); 
							})
							.defaultIfEmpty(new Account())
							.flatMap(account ->{
								return WebClient.create("http://localhost:8080").get().uri("/credit/customer/{nroDocument}", nroDocument)
								.retrieve().bodyToMono(Credit.class)
								.transformDeferred(it -> {
									ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
									return rcb.run(it, throwable -> Mono.empty()); 
								})
								.defaultIfEmpty(new Credit())
								.flatMap(credit->{
									report.setCustomer(customer);
									report.setAccounts(account);
									report.setCredits(credit);
									return Mono.just(report);
								});
							});
				});
		
	}

}
