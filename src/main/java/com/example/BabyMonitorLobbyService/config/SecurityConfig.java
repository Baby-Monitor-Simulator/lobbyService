package com.example.BabyMonitorLobbyService.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
// Zorgt ervoor dat @PreAuthorize werkt
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .authenticated();


        http
                .oauth2ResourceServer(oauth2 -> oauth2
                                      .jwt(jwt -> jwt
                                           .jwkSetUri("http://keycloak:8080/realms/Babymonitor/protocol/openid-connect/certs")));

        return http.build();
    }
}*/
