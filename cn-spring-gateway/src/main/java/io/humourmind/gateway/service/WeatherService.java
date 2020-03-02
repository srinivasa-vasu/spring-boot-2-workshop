package io.humourmind.gateway.service;

import io.humourmind.gateway.domain.Weather;
import reactor.core.publisher.Mono;

public interface WeatherService {
	Mono<Weather> getWeatherByPostalCode(String postalCode);
}
