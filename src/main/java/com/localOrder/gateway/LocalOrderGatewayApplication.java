package com.localOrder.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.localOrder.gateway.filter.TokenValidationFilter;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.localOrder.gateway"})
public class LocalOrderGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalOrderGatewayApplication.class, args);
	}
	
	

}
