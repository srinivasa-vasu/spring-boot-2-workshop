package io.humourmind.cnspringgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.cloudfoundry.discovery.EnableCloudFoundryClient;

@SpringBootApplication
@EnableCloudFoundryClient
public class CnSpringGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnSpringGatewayApplication.class, args);
	}
}
