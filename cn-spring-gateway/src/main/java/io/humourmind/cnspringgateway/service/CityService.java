package io.humourmind.cnspringgateway.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

import io.humourmind.cnspringgateway.domain.City;
import reactor.core.publisher.Mono;

@Service
public interface CityService {
	Mono<City> getCityByPostalCode(String postalCode, OAuth2AuthorizedClient authorizedClient);
}
