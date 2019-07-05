package io.humourmind.cnspringcity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuerUri;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//@formatter:off
		http
				.authorizeRequests().requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
				.and()
				.authorizeRequests().anyRequest().authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt();
		//@formatter:on
	}

	@Bean
	public JwtDecoder jwtDecoder() {
		NimbusJwtDecoderJwkSupport jwtDecoder = (NimbusJwtDecoderJwkSupport) JwtDecoders
				.fromOidcIssuerLocation(issuerUri);

		OAuth2TokenValidator<Jwt> jwtValidators = new DelegatingOAuth2TokenValidator<>(
				new JwtIssuerValidator(issuerUri), new JwtTimestampValidator(),
				new AudienceValidator());

		jwtDecoder.setJwtValidator(jwtValidators);

		return jwtDecoder;
	}

	class AudienceValidator implements OAuth2TokenValidator<Jwt> {
		private OAuth2Error error = new OAuth2Error("invalid_token",
				"The required audience is missing", null);

		@Override
		public OAuth2TokenValidatorResult validate(Jwt jwt) {
			if (jwt.getAudience().contains("city")) {
				return OAuth2TokenValidatorResult.success();
			}
			else {
				return OAuth2TokenValidatorResult.failure(error);
			}
		}
	}

}
