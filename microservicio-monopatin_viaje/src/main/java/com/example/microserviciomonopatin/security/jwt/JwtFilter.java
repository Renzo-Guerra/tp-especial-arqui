package com.example.microserviciomonopatin.security.jwt;

import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth";
    private final JwtParser jwtParser;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = this.getToken( httpServletRequest );
        try {
            if ( StringUtils.hasText( jwt ) && this.validateToken( jwt ) ) {
                Authentication authentication = this.getAuthentication( jwt );
                SecurityContextHolder.getContext().setAuthentication( authentication );
            }
        } catch ( RuntimeException e ) {
            throw new BadCredentialsException("Bad credentials.");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private String getToken(HttpServletRequest request) {
        String bearerToken = request.getHeader( AUTHORIZATION_HEADER );
        if ( StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") ) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken( String authToken ) {
        try {
            jwtParser.parseClaimsJws( authToken );
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException ignored ) {
            return false;
        }
    }

}