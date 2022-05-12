package com.customer.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.customer.service.client.Account;
import com.customer.service.client.Credit;
import com.customer.service.client.Report;
import com.customer.service.model.Customer;
import com.customer.service.service.CustomerService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<Mono<Customer>> createCustomer(@RequestBody Customer c) {
		c.setCreationDate(LocalDateTime.now());
		Mono<Customer> nuevoCustomer = customerService.addCustomer(c);
		return ResponseEntity.ok(nuevoCustomer);
	}

	@PutMapping
	public ResponseEntity<Mono<Customer>> updateCustomer(@RequestBody Customer c) {
		c.setModificationDate(LocalDateTime.now());
		Mono<Customer> updateCust = customerService.updateCustomer(c);
		return ResponseEntity.ok(updateCust);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mono<Customer>> getCustomerById(@PathVariable("id") int id) {
		Mono<Customer> customer = customerService.getCustomerById(id);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/{nroDocument}")
	public ResponseEntity<Mono<Customer>> getCustomerByNroDocument(@PathVariable("nroDocument") int nroDocument) {
		Mono<Customer> customer = customerService.getCustomerByNroDoc(nroDocument);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	@GetMapping("/{typeCustomer}")
	public ResponseEntity<Mono<Customer>> getCustomerByTypeCustomer(@PathVariable("typeCustomer") String typeCustomer) {
		Mono<Customer> customer = customerService.getCustomerByTypeCustomer(typeCustomer);
		if (customer == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(customer);
	}

	@GetMapping
	public ResponseEntity<Flux<Customer>> getAllCustomer() {
		Flux<Customer> customers = customerService.getAllCustomer();
		return ResponseEntity.ok(customers);
	}

	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") int id) {
		customerService.deleteCustomer(id);
	}

	// *******metodos webclient******************
	@PostMapping("/account/{nroDocument}")
	public ResponseEntity<Mono<Account>> saveAccount(@PathVariable("nroDocument") int nroDocument,
			@RequestBody Account a) {
		Mono<Account> nuevoAccount = customerService.addAccount(nroDocument, a);
		return ResponseEntity.ok(nuevoAccount);
	}

	@GetMapping("/account/{nroDocument}")
	public ResponseEntity<Mono<Account>> getAccountByNroDocument(@PathVariable("nroDocument") int nroDocument) {
		Mono<Account> account = customerService.getAccountByNroDoc(nroDocument);
		if (account == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(account);
	}

	@PostMapping("/credit/{nroDocument}")
	public ResponseEntity<Mono<Credit>> saveCredit(@PathVariable("nroDocument") int nroDocument,
			@RequestBody Credit credit) {
		Mono<Credit> nuevoCredit = customerService.addCredit(nroDocument, credit);
		return ResponseEntity.ok(nuevoCredit);
	}

	@GetMapping("/credit/{nroDocument}")
	public ResponseEntity<Mono<Credit>> getCreditByNroDocument(@PathVariable("nroDocument") int nroDocument) {
		Mono<Credit> credit = customerService.getCreditByNroDoc(nroDocument);
		if (credit == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(credit);
	}

	@GetMapping("/todos/{nroDocument}")
	public ResponseEntity<Mono<Report>> getAllProducts(@PathVariable("nroDocument") int nroDocument) {
		Mono<Report> resultado = customerService.getCustomerAndProducts(nroDocument);
		return ResponseEntity.ok(resultado);
	}

}
