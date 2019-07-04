package io.humourmind.cnspringasd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class CnSpringAsdApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnSpringAsdApplication.class, args);
	}
}
