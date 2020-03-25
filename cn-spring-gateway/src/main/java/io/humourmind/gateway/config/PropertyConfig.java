package io.humourmind.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "service")
public class PropertyConfig {

	private String city;
	private String weather;

	public String getCity() {
		return city;
	}

	void setCity(String city) {
		this.city = city;
	}

	public String getWeather() {
		return weather;
	}

	void setWeather(String weather) {
		this.weather = weather;
	}
}
