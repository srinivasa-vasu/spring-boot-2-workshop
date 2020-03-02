package io.humourmind.gateway.config;

import javax.net.ssl.SSLException;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.cloud.client.loadbalancer.reactive.ReactiveLoadBalancer.Factory;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		//@formatter:off
		return http
				.authorizeExchange().matchers(EndpointRequest.toAnyEndpoint()).permitAll()
				.and()
				.authorizeExchange().pathMatchers("/v1/**").permitAll()
				.and()
				.authorizeExchange().anyExchange().authenticated()
				.and().csrf().disable().headers().frameOptions().disable()
				.and().formLogin()
				.and().build();
		//@formatter:on
	}

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("user")
				.password(passwordEncoder().encode("user")).roles("").build();
		return new MapReactiveUserDetailsService(user);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebClient webClient(Factory factory) throws SSLException {

		SslContext sslContext = SslContextBuilder.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE).build();
		// HttpClient client = HttpClient.create().secure(sslContextSpec -> sslContextSpec
		// .sslContext(sslContext).handlerConfigurator(sslHandler -> {
		// SSLEngine engine = sslHandler.engine();
		// SSLParameters params = new SSLParameters();
		// List<SNIMatcher> matchers = new LinkedList<>();
		//
		// SNIMatcher matcher = new SNIMatcher(0) {
		// @Override
		// public boolean matches(SNIServerName serverName) {
		// return true;
		// }
		// };
		// matchers.add(matcher);
		// params.setSNIMatchers(matchers);
		// engine.setSSLParameters(params);
		// }));

		return WebClient.builder()
				.clientConnector(new ReactorClientHttpConnector(
						HttpClient.create().secure(t -> t.sslContext(sslContext))))
				.filter(new ReactorLoadBalancerExchangeFilterFunction(factory)).build();
	}
}
