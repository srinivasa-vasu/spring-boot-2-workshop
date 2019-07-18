package io.humourmind.cnspringgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("city-service",
						rt -> rt.path("/v1/cities/**").uri("lb://cn-city-service"))
				.route("weather-service",
						rt -> rt.path("/v1/weather/**").uri("lb://cn-weather-service"))
				.route("local-service",
						rt -> rt.path("/api/**").uri("lb://cn-gateway-service"))
				.build();
	}
}
