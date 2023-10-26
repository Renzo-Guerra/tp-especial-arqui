package com.example.microservicioparada.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    @Bean("monopatinClienteRest")
    public RestTemplate registrarRestTemplate(){ return new RestTemplate(); }
}
