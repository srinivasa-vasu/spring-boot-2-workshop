package io.humourmind.cnspringcity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.cnspringcity.domain.City;
import io.humourmind.cnspringcity.repository.CityRepository;

@RestController
public class CityController {

	private final CityRepository cityRepository;

	public CityController(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	@GetMapping("/v1/cities/{postalCode}")
	@PreAuthorize("hasAuthority('SCOPE_city.read')")
	public City getCityByPostalCode(@PathVariable String postalCode) {
		return cityRepository.findByPostalCodeIgnoreCase(postalCode, null).get()
				.findFirst().orElseGet(City::new);
	}

}
