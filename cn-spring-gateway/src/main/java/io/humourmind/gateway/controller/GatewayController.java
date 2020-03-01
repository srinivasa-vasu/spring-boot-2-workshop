package io.humourmind.gateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.gateway.domain.CityWeatherInfo;
import io.humourmind.gateway.service.CityService;
import io.humourmind.gateway.service.WeatherService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/data")
class GatewayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);
	private final CityService cityService;
	private final WeatherService weatherService;
	private final ReactiveCircuitBreakerFactory circuitBreakerFactory;

	public GatewayController(CityService cityService, WeatherService weatherService,
			ReactiveCircuitBreakerFactory circuitBreakerFactory) {
		this.cityService = cityService;
		this.weatherService = weatherService;
		this.circuitBreakerFactory = circuitBreakerFactory;
	}

	@GetMapping("/{postalCode}")
	public Mono<CityWeatherInfo> getAggregatedData(
			@PathVariable("postalCode") String postalCode) {
		LOGGER.info("Get aggregated data");
		//@formatter:off
		return circuitBreakerFactory.create("backend-service")
				.run(Mono.zip(cityService.getCityByPostalCode(postalCode), weatherService.getWeatherByPostalCode(postalCode))
						.map(tuple -> CityWeatherInfo.builder().city(tuple.getT1())
								.weather(tuple.getT2()).build()),
					throwable -> {
						LOGGER.error("Real-time service call failed; getting fallback service data", throwable);
						return Mono.zip(cityService.getCityByPostalCodeFallback(postalCode), weatherService.getWeatherByPostalCodeFallback(postalCode))
								.map(tuple -> CityWeatherInfo.builder().city(tuple.getT1())
										.weather(tuple.getT2()).build());
					});
		//@formatter:on
	}

}
