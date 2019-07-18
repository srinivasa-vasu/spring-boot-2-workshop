package io.humourmind.cnspringgateway.service;

import io.humourmind.cnspringgateway.domain.Weather;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

public interface WeatherService {
	Mono<Weather> getWeatherByPostalCode(String postalCode);
}
