package io.humourmind.cnspringgateway.config;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		//@formatter:off
		return http
				.authorizeExchange().matchers(EndpointRequest.toAnyEndpoint()).permitAll()
				.and()
				.authorizeExchange().anyExchange().authenticated()
				.and()
				.oauth2Login()
				.and()
				.logout()
				.and()
				.build();
		//@formatter:on
	}

	@Bean
	public WebClient webClient(
			ReactiveClientRegistrationRepository clientRegistrationRepository,
			ServerOAuth2AuthorizedClientRepository authorizedClientRepository,
			LoadBalancerClient loadBalancerClient) {
		ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2 = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
				clientRegistrationRepository, authorizedClientRepository);
		oauth2.setDefaultOAuth2AuthorizedClient(true);
		return WebClient.builder()
				.filter(oauth2)
				.filter(new LoadBalancerExchangeFilterFunction(loadBalancerClient))
				.build();
	}

}
