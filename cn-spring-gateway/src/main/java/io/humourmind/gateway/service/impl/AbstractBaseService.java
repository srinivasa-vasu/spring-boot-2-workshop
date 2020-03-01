package io.humourmind.gateway.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractBaseService {

	private final WebClient webClient;

	protected String serviceId;

	private String fallback;

	private String protocol;

	protected AbstractBaseService(@LoadBalanced WebClient webClient) {
		this.webClient = webClient;
	}

	final WebClient getWebClient() {
		return webClient;
	}

	abstract void setServiceId(String serviceId);

	String getFallback() {
		return fallback;
	}

	@Value("${service.fallback:cn-egateway-service}")
	void setFallback(String fallback) {
		this.fallback = fallback;
	}

	String getProtocol() {
		return protocol;
	}

	@Value("${service.protocol:http}")
	void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
