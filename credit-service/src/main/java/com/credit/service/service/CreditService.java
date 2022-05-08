package com.credit.service.service;

import com.credit.service.model.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

	Mono<Credit> addCredit(Credit c);

	Flux<Credit> getAllCredit();

	Mono<Credit> getCreditById(int idCredit);

	Mono<Credit> getCreditByNroDocument(int nroDocument);

	Mono<Credit> getCreditByTypeCredit(String typeCredit);

	void deleteCredit(int idCredit);
}
