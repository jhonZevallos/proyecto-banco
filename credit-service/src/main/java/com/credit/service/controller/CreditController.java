package com.credit.service.controller;

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

import com.credit.service.model.Credit;
import com.credit.service.service.CreditService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/credit")
public class CreditController {

	@Autowired
	private CreditService creditService;
	
	@PostMapping
	public ResponseEntity<Mono<Credit>> createCredit(@RequestBody Credit c){
		c.setMembershipDate(LocalDateTime.now());
		Mono<Credit> nuevoCredit = creditService.addCredit(c);
		return ResponseEntity.ok(nuevoCredit);
	}
	
	@GetMapping("{idCredit}")
	public ResponseEntity<Mono<Credit>> getCreditById(@PathVariable("idCredit") int idCredit){
		Mono<Credit> credit = creditService.getCreditById(idCredit);
		if(credit == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(credit);
	}
	@GetMapping("/customer/{nroDocument}")
	public ResponseEntity<Mono<Credit>> getCreditByNroDocument(@PathVariable("nroDocument") int nroDocument){
		Mono<Credit> credit = creditService.getCreditByNroDocument(nroDocument);
		if(credit == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(credit);
	}
	
	@GetMapping
	public ResponseEntity<Flux<Credit>> getAllCredit(){
		Flux<Credit> credit = creditService.getAllCredit();
		return ResponseEntity.ok(credit);
	}
	
	@DeleteMapping("/{idCredit}")
	public void deleteCredit(@PathVariable("idCredit") int idCredit) {
		creditService.deleteCredit(idCredit);
	}






















}
