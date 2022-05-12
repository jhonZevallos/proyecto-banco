package com.yanky.service.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.yanky.service.model.Yanki;

public interface YankiRepository extends ReactiveMongoRepository<Yanki, String>{

}
