package io.humourmind.cnspringgateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import io.humourmind.cnspringgateway.domain.Weather;
import io.humourmind.cnspringgateway.service.WeatherService;
import reactor.core.publisher.Mono;

@Service
public class WeatherServiceImpl extends AbstractBaseService implements WeatherService {

	protected WeatherServiceImpl(WebClient webClient) {
		super(webClient);
	}

	@Override
	public Mono<Weather> getWeatherByPostalCode(String postalCode) {
		return getWebClient().get()
				.uri("{protocol}://{serviceId}/v1/weather/{postalCode}", getProtocol(), serviceId, postalCode)
				.retrieve().bodyToMono(Weather.class);
	}

	@Override
    @Value("${service.weather:cn-weather-service}")
	void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

}
