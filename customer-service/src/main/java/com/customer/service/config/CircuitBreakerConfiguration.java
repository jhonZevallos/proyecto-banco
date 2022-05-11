package com.customer.service.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

public class CircuitBreakerConfiguration {

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> serviceCusomtizer() {
		return factory -> {
		factory.configure(builder -> builder
		.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
		.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()), "serviceCB");
		};
		}
}
