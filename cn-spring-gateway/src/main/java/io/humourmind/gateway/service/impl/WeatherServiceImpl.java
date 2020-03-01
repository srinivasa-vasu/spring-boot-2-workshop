package io.humourmind.gateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.humourmind.gateway.domain.Weather;
import io.humourmind.gateway.service.WeatherService;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl extends AbstractBaseService implements WeatherService {

	protected WeatherServiceImpl(WebClient webClient) {
		super(webClient);
	}

	@Override
	public Mono<Weather> getWeatherByPostalCode(String postalCode) {
		return getWebClient()
				.get().uri("{protocol}://{serviceId}/v1/weather/{postalCode}",
						getProtocol(), serviceId, postalCode)
				.retrieve().bodyToMono(Weather.class);
	}

	@Override
	public Mono<Weather> getWeatherByPostalCodeFallback(String postalCode) {
		return getWebClient()
				.get().uri("{protocol}://{serviceId}/cf/v1/weather/{postalCode}",
						getProtocol(), getFallback(), postalCode)
				.retrieve().bodyToMono(Weather.class);
	}

	@Override
	@Value("${service.weather:cn-weather-service}")
	void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
