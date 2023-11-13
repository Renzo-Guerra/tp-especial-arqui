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
                .route("lll", r -> r.path("/api/authenticate" )
                        .uri("http://localhost:8081"))
                .route("auth-service", r -> r.path("/api/register" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8081"))
                .route("micro-a-product", r -> r.path( "/api/admin/products/**" )
                        .filters( f ->
                                f.filter( authFilter.apply( new AuthenticationFilter.Config() ) )
                        )
                        .uri("http://localhost:8082"))
                .route("micro-a-product", r -> r.path("/api/products/**")
                        .filters(f ->
                            f.filter(authFilter.apply(new AuthenticationFilter.Config()))
                        )
                        .uri("http://localhost:8082"))
                .build();
    }

}
