package com.example.BabyMonitorLobbyService;

import com.example.BabyMonitorLobbyService.service.DotEnvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BabyMonitorLobbyServiceApplication {

	public static void main(String[] args) {
		DotEnvLoader.loadEnv();
		SpringApplication.run(BabyMonitorLobbyServiceApplication.class, args);
	}

}
