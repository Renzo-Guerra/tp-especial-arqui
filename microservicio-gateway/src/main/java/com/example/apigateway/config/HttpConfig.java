package com.example.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class HttpConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain( ServerHttpSecurity http ) {
        return http
                    .csrf( ServerHttpSecurity.CsrfSpec::disable )
                    .authorizeExchange(exchanges -> exchanges.anyExchange().permitAll() )
                    .build();
    }
}
