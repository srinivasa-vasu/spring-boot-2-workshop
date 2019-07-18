package io.humourmind.cnspringgateway.service;

import io.humourmind.cnspringgateway.domain.City;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Service;

public interface CityService {
	Mono<City> getCityByPostalCode(String postalCode);
}
