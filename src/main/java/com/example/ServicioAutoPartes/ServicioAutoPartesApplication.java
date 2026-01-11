package com.example.ServicioAutoPartes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

@EnableDiscoveryClient
@SpringBootApplication
public class ServicioAutoPartesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioAutoPartesApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.modulesToInstall(Hibernate5Module.class);
		return builder;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new Jackson2ObjectMapperBuilder()
			.modulesToInstall(new Hibernate5Module())
			.build();
	}

}
