package com.example.ServicioAutoPartes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ServicioAutoPartesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAutoPartesApplication.class, args);
	}

}
