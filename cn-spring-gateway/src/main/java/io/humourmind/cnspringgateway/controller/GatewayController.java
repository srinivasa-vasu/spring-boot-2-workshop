package io.humourmind.cnspringgateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.commons.CircuitBreakerFactory;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.cnspringgateway.domain.City;
import io.humourmind.cnspringgateway.domain.CityWeatherInfo;
import io.humourmind.cnspringgateway.domain.Weather;
import io.humourmind.cnspringgateway.service.CityService;
import io.humourmind.cnspringgateway.service.WeatherService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/data")
class GatewayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);
	private final CityService cityService;
	private final WeatherService weatherService;
	private final CircuitBreakerFactory circuitBreakerFactory;

	private final Map<String, City> cityCacheMap = new HashMap<>();
	private final Map<String, Weather> weatherCacheMap = new HashMap<>();

	public GatewayController(CityService cityService, WeatherService weatherService,
			CircuitBreakerFactory circuitBreakerFactory) {
		this.cityService = cityService;
		this.weatherService = weatherService;
		this.circuitBreakerFactory = circuitBreakerFactory;
	}

	@GetMapping("/{postalCode}")
	public Mono<CityWeatherInfo> getAggregatedData(
			@PathVariable("postalCode") String postalCode,
			@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
		LOGGER.info("Get aggregated data");
		return circuitBreakerFactory.create("backend-service")
				.run(() -> Mono
						.zip(cityService.getCityByPostalCode(postalCode, authorizedClient),
								weatherService.getWeatherByPostalCode(postalCode, authorizedClient))
						.flatMap(tuple -> {
							cityCacheMap.put(tuple.getT1().getPostalCode(),
									tuple.getT1());
							weatherCacheMap.put(tuple.getT2().getPostalCode(),
									tuple.getT2());
							return Mono.just(CityWeatherInfo.builder().city(tuple.getT1())
									.weather(tuple.getT2()).build());
						}),
					throwable -> {
						LOGGER.error("Real-time service call failed; getting fallback service data", throwable);
						return Mono.just(CityWeatherInfo.builder().city(cityCacheMap.get(postalCode))
								.weather(weatherCacheMap.get(postalCode)).build());
				});
	}
}
