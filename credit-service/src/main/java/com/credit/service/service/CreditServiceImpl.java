package com.credit.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.credit.service.model.Credit;
import com.credit.service.repository.CreditRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

	@Autowired
	private CreditRepository creditRepository;

	@Override
	public Mono<Credit> addCredit(Credit c) {

		return creditRepository.save(c);
	}

	@Override
	public Flux<Credit> getAllCredit() {

		return creditRepository.findAll();
	}

	@Override
	public Mono<Credit> getCreditById(int idCredit) {

		return creditRepository.findById(idCredit);
	}

	@Override
	public Mono<Credit> getCreditByNroDocument(int nroDocument) {

		return creditRepository.findByNroDocument(nroDocument);
	}

	@Override
	public Mono<Credit> getCreditByTypeCredit(String typeCredit) {

		return creditRepository.findByTypeCredit(typeCredit);
	}

	@Override
	public void deleteCredit(int idCredit) {

		creditRepository.deleteById(idCredit);

	}
}
