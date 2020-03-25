package io.humourmind.gateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.gateway.domain.City;
import io.humourmind.gateway.domain.CityWeatherInfo;
import io.humourmind.gateway.domain.Weather;
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

	private final Map<String, City> cityCacheMap = new HashMap<>();
	private final Map<String, Weather> weatherCacheMap = new HashMap<>();

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
		return Mono.zip(
					circuitBreakerFactory.create("cn-city-service")
						.run(cityService.getCityByPostalCode(postalCode)
							.map(res -> {
								cityCacheMap.put(postalCode, res);
								return res;
							}),
						throwable -> {
							LOGGER.error("Real-time city-service call failed; getting fallback service data", throwable);
							return Mono.just(cityCacheMap.getOrDefault(postalCode, new City()));
						}),
					circuitBreakerFactory.create("cn-weather-service")
						.run(weatherService.getWeatherByPostalCode(postalCode)
							.map(res -> {
								weatherCacheMap.put(postalCode, res);
								return res;
							}),
						throwable -> {
							LOGGER.error("Real-time weather-service call failed; getting fallback service data", throwable);
							return Mono.just(weatherCacheMap.getOrDefault(postalCode, new Weather()));
						})
				).map(tuple -> CityWeatherInfo.builder().city(tuple.getT1())
						.weather(tuple.getT2()).build());
		//@formatter:on
	}
}
