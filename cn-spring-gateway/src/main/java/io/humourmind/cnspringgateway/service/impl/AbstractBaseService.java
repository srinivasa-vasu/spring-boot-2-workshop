package io.humourmind.cnspringgateway.service.impl;

import org.springframework.web.reactive.function.client.WebClient;

public abstract class AbstractBaseService {

	private final WebClient webClient;

	protected String serviceId;

	protected AbstractBaseService(WebClient webClient) {
		this.webClient = webClient;
	}

	final WebClient getWebClient() {
		return webClient;
	}

	abstract void setServiceId(String serviceId);
}
