package com.example.BabyMonitorLobbyService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.example.BabyMonitorLobbyService.repository")
public class BabyMonitorLobbyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BabyMonitorLobbyServiceApplication.class, args);
	}

}
