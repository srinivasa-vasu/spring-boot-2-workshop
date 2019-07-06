package io.humourmind.cnspringgateway.service.implementation;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.humourmind.cnspringgateway.domain.City;
import io.humourmind.cnspringgateway.service.CityService;
import reactor.core.publisher.Mono;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

@Service
public class CityServiceImpl implements CityService {

	private final WebClient webClient;

	@Value("${endpoint.city:cn-city-service}")
	private String cityEndPoint;

	public CityServiceImpl(WebClient webClient) {
		this.webClient = webClient;
	}

	@Override
	public Mono<City> getCityByPostalCode(String postalCode, OAuth2AuthorizedClient authorizedClient) {
		return webClient.get()
				.uri("http://{endpoint}/v1/cities/{postalCode}", cityEndPoint, postalCode)
				.attributes(oauth2AuthorizedClient(authorizedClient))
				.retrieve().bodyToMono(City.class);
	}
}
