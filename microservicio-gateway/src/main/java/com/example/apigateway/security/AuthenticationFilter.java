package com.example.apigateway.security;

import com.example.apigateway.dto.ValidateTokenDTO;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
import java.util.function.Predicate;

/**
 * Authentication pre-filter for API gateway.
 */
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final String _AuthHeader = "Authorization";
    List<String> excludedUrls = List.of( "api/authenticate" , "api/register", "api/validate");
    private final WebClient.Builder webClientBuilder;

    public AuthenticationFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply( Config config ) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String bearerToken = request.getHeaders().getFirst( _AuthHeader );

            if( this.isSecured.test( request ) ) {
                return webClientBuilder.build().get()
                        .uri("http://localhost:8081/api/validate")
                        .header( _AuthHeader, bearerToken )
                        .retrieve().bodyToMono( ValidateTokenDTO.class )
                        .map( response -> {
                            if( ! response.isAuthenticated() )
                                throw new BadCredentialsException( "JWT invalido" );
                            return exchange;
                        })
                        .flatMap(chain::filter);
            }
            return chain.filter(exchange);
        };
    }

    private final Predicate<ServerHttpRequest> isSecured = request -> excludedUrls.stream().noneMatch(uri -> request.getURI().getPath().contains(uri) );


    /**
     * Required by AbstractGatewayFilterFactory
     */
    @NoArgsConstructor
    public static class Config {}

}
