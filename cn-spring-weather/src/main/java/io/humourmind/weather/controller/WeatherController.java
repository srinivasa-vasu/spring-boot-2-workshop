package io.humourmind.weather.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.humourmind.weather.domain.Weather;
import io.humourmind.weather.repository.WeatherRepository;

@RestController
public class WeatherController {

	private final WeatherRepository weatherRepository;

	public WeatherController(WeatherRepository weatherRepository) {
		this.weatherRepository = weatherRepository;
	}

	@RequestMapping("/v1/weather/{postalCode}")
	public Weather getWeatherByPostalCode(@PathVariable String postalCode) {
		return weatherRepository.findByPostalCodeIgnoreCase(postalCode, null).get()
				.findFirst().orElseGet(Weather::new);
	}

}
