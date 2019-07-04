package io.humourmind.cnspringgateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.humourmind.cnspringgateway.domain.City;

@FeignClient(name = "cn-city-service")
public interface CityService {
	@GetMapping("/v1/cities/{postalCode}")
	City getCityByPostalCode(@PathVariable("postalCode") String postalCode);
}
