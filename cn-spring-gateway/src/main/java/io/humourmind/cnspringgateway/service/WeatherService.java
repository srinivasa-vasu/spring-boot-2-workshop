package io.humourmind.cnspringgateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.humourmind.cnspringgateway.domain.Weather;

@FeignClient(name = "cn-weather-service")
public interface WeatherService {
	@GetMapping("/v1/weather/{postalCode}")
	Weather getWeatherByPostalCode(@PathVariable("postalCode") String postalCode);
}
