package io.humourmind.cnspringweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CnSpringWeatherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnSpringWeatherApplication.class, args);
	}
}
