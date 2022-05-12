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

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ReactiveCircuitBreakerFactory reactiveCircuitBreakerFactory;

	@Override
	public Mono<Customer> addCustomer(Customer c) {

		return customerRepository.save(c).switchIfEmpty(Mono.error(new InterruptedException("Error on addCustomer")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Mono<Customer> updateCustomer(Customer c) {
		int idCustomer = c.getId();
		if (idCustomer < 0) {
			c.setId(idCustomer);
		}
		return customerRepository.save(c).switchIfEmpty(Mono.error(new InterruptedException("Error on updateCustomer")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Flux<Customer> getAllCustomer() {

		return customerRepository.findAll()
				.switchIfEmpty(Flux.error(new InterruptedException("Error on getAllCustomer")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Flux.empty());
	}

	@Override
	public Mono<Customer> getCustomerById(int id) {

		return customerRepository.findById(id)
				.switchIfEmpty(Mono.error(new InterruptedException("Error on getCustomerById")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Mono<Customer> getCustomerByNroDoc(int nroDocument) {

		return customerRepository.findByNroDocument(nroDocument)
				.switchIfEmpty(Mono.error(new InterruptedException("Error on getCustomerByNroDoc")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public void deleteCustomer(int id) {
		customerRepository.deleteById(id).switchIfEmpty(Mono.error(new InterruptedException("Error on deleteCustomer")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());

	}

	@Override
	public Mono<Customer> getCustomerByTypeCustomer(String typeCustomer) {

		return customerRepository.findByTypeCustomer(typeCustomer)
				.switchIfEmpty(Mono.error(new InterruptedException("Error on getCustomerByTypeCustomer")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	// ************************************METODOS
	// WebClient*****************************************

	@Override
	public Mono<Account> getAccountByNroDoc(int nroDocument) {
		Mono<Account> account = WebClient.create("http://localhost:8080").get().uri("/account/customer/" + nroDocument)
				.retrieve().bodyToMono(Account.class).transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty());
				}).switchIfEmpty(Mono.error(new InterruptedException("Error on getAccountByNroDoc")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
		return account;
	}

	@Override
	public Mono<Account> addAccount(int nroDocument, Account a) {
		a.setNroDocument(nroDocument);
		Mono<Account> nuevoAccount = WebClient.create("http://localhost:8080").post().uri("/account")
				.body(Mono.just(a), Account.class).retrieve().bodyToMono(Account.class).transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty());
				}).switchIfEmpty(Mono.error(new InterruptedException("Error on addAccount")))
				.doOnError(ex -> log.error(ex.getMessage()))
				.onErrorResume(ex -> Mono.empty());
		return nuevoAccount;
	}

	@Override
	public Mono<Credit> getCreditByNroDoc(int nroDocument) {
		Mono<Credit> credit = WebClient.create("http://localhost:8080").get().uri("/credit/customer/" + nroDocument)
				.retrieve().bodyToMono(Credit.class).transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty());
				}).switchIfEmpty(Mono.error(new InterruptedException("Error on getCreditByNroDoc")))
				.doOnError(ex -> log.error(ex.getMessage()))
				.onErrorResume(ex -> Mono.empty());
		return credit;
	}

	@Override
	public Mono<Credit> addCredit(int nroDocument, Credit credit) {
		credit.setNroDocument(nroDocument);
		Mono<Credit> nuevoCredit = WebClient.create("http://localhost:8080").post().uri("/credit")
				.body(Mono.just(credit), Credit.class).retrieve().bodyToMono(Credit.class).transformDeferred(it -> {
					ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
					return rcb.run(it, throwable -> Mono.empty());
				}).switchIfEmpty(Mono.error(new InterruptedException("Error on addCredit")))
				.doOnError(ex -> log.error(ex.getMessage()))
				.onErrorResume(ex -> Mono.empty());
		return nuevoCredit;
	}

	@Override
	public Mono<Report> getCustomerAndProducts(int nroDocument) {
		Report report = new Report();

		return customerRepository.findByNroDocument(nroDocument).switchIfEmpty(Mono.error(new InterruptedException("No existe ese numero de documento"))).flatMap(customer -> {
			return WebClient.create("http://localhost:8080").get().uri("/account/customer/{nroDocument}", nroDocument)
					.retrieve().bodyToMono(Account.class).transformDeferred(it -> {
						ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
						return rcb.run(it, throwable -> Mono.empty());
					}).defaultIfEmpty(new Account()).flatMap(account -> {
						return WebClient.create("http://localhost:8080").get()
								.uri("/credit/customer/{nroDocument}", nroDocument).retrieve().bodyToMono(Credit.class)
								.transformDeferred(it -> {
									ReactiveCircuitBreaker rcb = reactiveCircuitBreakerFactory.create("serviceCB");
									return rcb.run(it, throwable -> Mono.empty());
								}).defaultIfEmpty(new Credit()).flatMap(credit -> {
									report.setCustomer(customer);
									report.setAccounts(account);
									report.setCredits(credit);
									return Mono.just(report);
								});
					});
		}).doOnError(ex -> log.error(ex.getMessage()))
				.onErrorResume(ex ->Mono.empty());

	}

}
