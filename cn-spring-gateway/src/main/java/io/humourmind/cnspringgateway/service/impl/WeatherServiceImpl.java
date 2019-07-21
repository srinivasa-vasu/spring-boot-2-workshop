package io.humourmind.cnspringgateway.service.impl;

import io.humourmind.cnspringgateway.config.LoadBalancerClientConfig;
import io.humourmind.cnspringgateway.domain.Weather;
import io.humourmind.cnspringgateway.service.WeatherService;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RibbonClient(name = "cn-weather-service", configuration = LoadBalancerClientConfig.class)
public class WeatherServiceImpl extends AbstractBaseService implements WeatherService {

    protected WeatherServiceImpl(WebClient webClient) {
        super(webClient);
    }

    @Override
    public Mono<Weather> getWeatherByPostalCode(String postalCode) {
        return getWebClient().get()
                .uri("http://{serviceId}/v1/weather/{postalCode}", serviceId, postalCode)
                .retrieve().bodyToMono(Weather.class);
    }

    @Override
    @Value("${serviceId.weather:cn-weather-service}")
    void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }


}
