package io.humourmind.gateway.service;

import io.humourmind.gateway.domain.City;
import reactor.core.publisher.Mono;

public interface CityService {
	Mono<City> getCityByPostalCode(String postalCode);

	Mono<City> getCityByPostalCodeFallback(String postalCode);
}
