package io.humourmind.gateway.config;

import java.lang.reflect.Field;
import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.micrometer.tagged.TaggedCircuitBreakerMetrics;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.SneakyThrows;

@Configuration
public class FallbackConfig {

	private final PropertyConfig config;

	public FallbackConfig(PropertyConfig config) {
		this.config = config;
	}

	@SneakyThrows
	@Bean
	public CircuitBreakerRegistry circuitBreakerRegistry() {
		CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry
				.of(CircuitBreakerConfig.from(CircuitBreakerConfig.ofDefaults())
						.minimumNumberOfCalls(10).failureRateThreshold(10)
						.slowCallRateThreshold(5).slowCallRateThreshold(2).build());
		for (Field item : PropertyConfig.class.getDeclaredFields()) {
			item.setAccessible(true);
			circuitBreakerRegistry.circuitBreaker(String.valueOf(item.get(config)));
		}
		return circuitBreakerRegistry;
	}

	@Bean
	public Customizer<ReactiveResilience4JCircuitBreakerFactory> customizer() {
		return factory -> {
			factory.configureCircuitBreakerRegistry(circuitBreakerRegistry());
			factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
					.timeLimiterConfig(TimeLimiterConfig.custom()
							.timeoutDuration(Duration.ofSeconds(3)).build())
					.circuitBreakerConfig(CircuitBreakerConfig.custom()
							.minimumNumberOfCalls(10).failureRateThreshold(10)
							.slowCallRateThreshold(5).slowCallRateThreshold(2).build())
					.build());
		};
	}

	@Bean
	public ReactiveResilience4JCircuitBreakerFactory reactiveResilience4JCircuitBreakerFactory(
			MeterRegistry meterRegistry) {
		ReactiveResilience4JCircuitBreakerFactory factory = new ReactiveResilience4JCircuitBreakerFactory();
		customizer().customize(factory);
		TaggedCircuitBreakerMetrics.ofCircuitBreakerRegistry(circuitBreakerRegistry())
				.bindTo(meterRegistry);
		return factory;
	}

}
