package io.humourmind.cnspringgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.cloudfoundry.discovery.EnableCloudFoundryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import io.humourmind.cnspringgateway.config.LoadBalancerClientConfig;

@SpringBootApplication
@EnableCloudFoundryClient
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = LoadBalancerClientConfig.class))
public class CnSpringGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnSpringGatewayApplication.class, args);
	}
}
