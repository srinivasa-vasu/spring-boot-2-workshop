package io.humourmind.cnspringgateway.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.commons.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.cnspringgateway.domain.City;
import io.humourmind.cnspringgateway.domain.Weather;
import io.humourmind.cnspringgateway.service.CityService;
import io.humourmind.cnspringgateway.service.WeatherService;

@RestController
@RequestMapping("/api/v1/data")
class GatewayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayController.class);
	private final CityService cityService;
	private final WeatherService weatherService;
	private final CircuitBreakerFactory circuitBreakerFactory;

	private final Map<String, String> cityCacheMap = new HashMap<>();
	private final Map<String, String> weatherCacheMap = new HashMap<>();

	public GatewayController(CityService cityService, WeatherService weatherService,
			CircuitBreakerFactory circuitBreakerFactory) {
		this.cityService = cityService;
		this.weatherService = weatherService;
		this.circuitBreakerFactory = circuitBreakerFactory;
	}

	@GetMapping("/{postalCode}")
	public String getAggregatedData(@PathVariable("postalCode") String postalCode) {
		LOGGER.info("Get aggregated data");
		return String.format("%s", circuitBreakerFactory.create("backend-service").run(
				() -> getData(postalCode), throwable -> getFallBackData(postalCode)));

	}

	private String getData(String postalCode) {
		return String.format("%s = %s", getCityByPostalCode(postalCode).getName(),
				getWeatherByPostalCode(postalCode).getWeather());
	}

	private City getCityByPostalCode(String postalCode) {
		LOGGER.info("Get real time city data");
		City city = cityService.getCityByPostalCode(postalCode);
		cityCacheMap.put(city.getPostalCode(), city.getName());
		return city;
	}

	private Weather getWeatherByPostalCode(String postalCode) {
		LOGGER.info("Get real time weather data");
		Weather weather = weatherService.getWeatherByPostalCode(postalCode);
		weatherCacheMap.put(weather.getPostalCode(), weather.getWeather());
		return weather;
	}

	private String getFallBackData(String postalCode) {
		LOGGER.info("Get fallback city and weather data");
		return String.format("%s = %s",
				cityCacheMap.getOrDefault(postalCode, new City().getName()),
				weatherCacheMap.getOrDefault(postalCode, new Weather().getWeather()));
	}

}
