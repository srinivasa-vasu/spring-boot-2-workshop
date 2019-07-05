package io.humourmind.cnspringweather.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.cnspringweather.domain.Weather;
import io.humourmind.cnspringweather.repository.WeatherRepository;

@RestController
public class WeatherController {

	private final WeatherRepository weatherRepository;

	public WeatherController(WeatherRepository weatherRepository) {
		this.weatherRepository = weatherRepository;
	}

	@GetMapping("/v1/weather/{postalCode}")
	@PreAuthorize("hasAuthority('SCOPE_weather.read')")
	public Weather getWeatherByPostalCode(@PathVariable String postalCode) {
		return weatherRepository.findByPostalCodeIgnoreCase(postalCode, null).get()
				.findFirst().orElseGet(Weather::new);
	}

}
