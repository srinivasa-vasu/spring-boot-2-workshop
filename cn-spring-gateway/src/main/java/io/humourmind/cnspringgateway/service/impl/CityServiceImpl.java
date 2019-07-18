package io.humourmind.cnspringgateway.service.impl;

import io.humourmind.cnspringgateway.config.LoadBalancerClientConfig;
import io.humourmind.cnspringgateway.domain.City;
import io.humourmind.cnspringgateway.domain.Weather;
import io.humourmind.cnspringgateway.service.CityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RibbonClient(name = "cn-city-service", configuration = LoadBalancerClientConfig.class)
public class CityServiceImpl extends AbstractBaseService implements CityService {

    protected CityServiceImpl(WebClient webClient) {
        super(webClient);
    }

    @Override
    public Mono<City> getCityByPostalCode(String postalCode) {
        return getWebClient().get()
                .uri( "http://{serviceId}/v1/cities/{postalCode}", serviceId, postalCode)
                .retrieve()
                .bodyToMono(City.class).onErrorReturn(new City());
    }

    @Override
    @Value("${serviceId.city:cn-city-service}")
    void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
