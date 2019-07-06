package io.humourmind.cnspringgateway.service.implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.humourmind.cnspringgateway.domain.Weather;
import io.humourmind.cnspringgateway.service.WeatherService;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Service
public class WeatherServiceImpl implements WeatherService {

	private final WebClient webClient;

	@Value("${endpoint.weather:cn-weather-service}")
	private String weatherEndPoint;

	public WeatherServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Mono<Weather> getWeatherByPostalCode(String postalCode, OAuth2AuthorizedClient authorizedClient) {
		return webClient.get()
				.uri("http://{endpoint}/v1/weather/{postalCode}", weatherEndPoint, postalCode)
				.attributes(oauth2AuthorizedClient(authorizedClient))
				.retrieve().bodyToMono(Weather.class);
	}
}
