package com.yanky.service.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yanky.service.model.Yanki;
import com.yanky.service.service.YankiService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/yanki")
public class YankiController {

	@Autowired
	private YankiService yankiService;

	@GetMapping
	public ResponseEntity<Flux<Yanki>> getAllYanki() {
		Flux<Yanki> yankis = yankiService.findAll();
		return ResponseEntity.ok(yankis);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Mono<Yanki>> getYankiById(@PathVariable("id") String id) {
		Mono<Yanki> yanki = yankiService.getYanki(id);
		if (yanki == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(yanki);
	}

	@PostMapping
	public ResponseEntity<Mono<Yanki>> createYanki(@RequestBody Yanki yanki) {
		yanki.setCreateDate(LocalDateTime.now());
		Mono<Yanki> nuevoYanki = yankiService.save(yanki);
		return ResponseEntity.ok(nuevoYanki);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Mono<Yanki>> updateYanki(@PathVariable("id") String id, @RequestBody Yanki yanki) {
		Mono<Yanki> updateYanki = yankiService.update(id, yanki);
		return ResponseEntity.ok(updateYanki);
	}

	@DeleteMapping("/{id}")
	public void deleteYanki(@PathVariable("id") String id) {
		yankiService.delete(id);
	}

}
