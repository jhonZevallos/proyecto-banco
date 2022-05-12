package com.yanky.service.service;

import com.yanky.service.model.Yanki;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiService {

	Flux<Yanki> findAll();

    Mono<Yanki> save(Yanki yanki);

    Mono<Yanki> update(String id, Yanki yanki);

    Mono<Void> delete(String id);

    Mono<Yanki> getYanki(String id);
}
