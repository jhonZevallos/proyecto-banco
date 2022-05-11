package com.customer.service.client;

import com.customer.service.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Report {
	
	private Customer customer;
	
	private Account accounts;
	
	private Credit credits;
	
	
}
