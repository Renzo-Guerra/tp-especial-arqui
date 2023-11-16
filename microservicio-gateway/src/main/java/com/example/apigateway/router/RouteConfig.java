package com.example.apigateway.router;

import com.example.apigateway.security.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routes( RouteLocatorBuilder builder, AuthenticationFilter authFilter ) {
        return builder.routes()
                .route("autenticar", r -> r.path("/api/authenticate" )
                        .uri("http://localhost:8081"))
                .route("registrar", r -> r.path("/api/register" )
                        .uri("http://localhost:8081"))
                .route("administracion", r -> r.path( "/administracion/**" )
                        .filters( f -> f.filter( authFilter.apply( new AuthenticationFilter.Config() ) ))
                        .uri("http://localhost:8001"))
                .route("monopatin", r -> r.path("/monopatines/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8002"))
                .route("viaje", r -> r.path("/viajes/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8002"))
                .route("parada", r -> r.path("/paradas/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:27017"))
                .route("usuario", r -> r.path("/usuarios/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8004"))
                .route("cuenta", r -> r.path("/cuentas/**")
                        .filters(f -> f.filter(authFilter.apply(new AuthenticationFilter.Config())))
                        .uri("http://localhost:8004"))
                .build();
    }

}
