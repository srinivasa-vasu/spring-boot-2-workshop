package io.humourmind.cnspringgateway.service;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;

import io.humourmind.cnspringgateway.domain.Weather;
import reactor.core.publisher.Mono;

@Service
public interface WeatherService {
	Mono<Weather> getWeatherByPostalCode(String postalCode, OAuth2AuthorizedClient authorizedClient);
}
