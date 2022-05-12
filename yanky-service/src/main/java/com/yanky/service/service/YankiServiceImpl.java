package com.yanky.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.yanky.service.model.Yanki;
import com.yanky.service.repository.YankiRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "cache.enabled", havingValue = "false")
public class YankiServiceImpl implements YankiService {

	@Autowired
	private YankiRepository repository;

	@Override
	public Flux<Yanki> findAll() {

		return repository.findAll().switchIfEmpty(Flux.error(new InterruptedException("Error on getAllYanki")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Flux.empty());
	}

	@Override
	public Mono<Yanki> save(Yanki yanki) {

		return repository.save(yanki).switchIfEmpty(Mono.error(new InterruptedException("Error on saveYanki")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Mono<Yanki> update(String id, Yanki yanki) {
		Mono<Yanki> updateYanki = repository.findById(id);
		if (updateYanki != null) {
			updateYanki = repository.save(yanki);
		}
		return updateYanki.switchIfEmpty(Mono.error(new InterruptedException("Error on updateYanki")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Mono<Void> delete(String id) {
		
		return repository.deleteById(id).switchIfEmpty(Mono.error(new InterruptedException("Error on deleteYanki")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

	@Override
	public Mono<Yanki> getYanki(String id) {
		
		return repository.findById(id).switchIfEmpty(Mono.error(new InterruptedException("Error on getYankiById")))
				.doOnError(ex -> log.error(ex.getMessage())).onErrorResume(ex -> Mono.empty());
	}

}
