package io.humourmind.cnspringgateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

	//@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("city-service",
						rt -> rt.path("/v1/cities/**").uri("http://cn-city-service.apps.internal:8080"))
				.route("weather-service",
						rt -> rt.path("/v1/weather/**").uri("http://cn-weather-service.apps.internal:8080"))
				.route("local-service",
						rt -> rt.path("/api/**").uri("http://cn-gateway-service.apps.internal:8080"))
				.build();
	}
}
