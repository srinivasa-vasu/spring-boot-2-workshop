package io.pivotal.cnspringsecurity;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
class RequestController {

	@Autowired
	private RestTemplate restTemplate;

	private static final String FORWARDED_URL = "X-CF-Forwarded-Url";
	private static final String PROXY_METADATA = "X-CF-Proxy-Metadata";
	private static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";

	@GetMapping(headers = { FORWARDED_URL, PROXY_METADATA, PROXY_SIGNATURE })
	public ResponseEntity<byte[]> interceptRequest(RequestEntity<byte[]> input) {
	    log.info("Incoming request >>>" + input);

		HttpHeaders header = new HttpHeaders();
		header.putAll(input.getHeaders());

		URI uri = header.remove(FORWARDED_URL).stream().findFirst()
				.map(URI::create).orElseThrow(
						() -> new IllegalStateException(FORWARDED_URL + "not found"));
		RequestEntity<byte[]> output = new RequestEntity<>(input.getBody(), header,
				input.getMethod(), uri);
		log.info("Outgoing request >>>" + output);
		return this.restTemplate.exchange(output, byte[].class);

	}

}
