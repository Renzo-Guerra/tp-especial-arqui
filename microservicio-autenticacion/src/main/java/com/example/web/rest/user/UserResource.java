package com.example.web.rest.user;

import com.example.security.jwt.JWTFilter;
import com.example.security.jwt.TokenProvider;
import com.example.service.UserService;
import com.example.service.constant.AuthorityConstant;
import com.example.service.dto.request.AuthRequestDTO;
import com.example.service.dto.user.request.UserRequestDTO;
import com.example.service.dto.user.response.UserResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping( "/api" )
@RequiredArgsConstructor
public class UserResource {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;

    // Valida el token y devuelve un JSON con nombre de usuario y sus autoridades.
    @GetMapping("/validate")
    public ResponseEntity<ValidateTokenDTO> validateGet() {
        // Obtenemos el token del usuario a traves del header?
        final var user = SecurityContextHolder.getContext().getAuthentication();
        // Pasamos a una lista los permisos que tenga el usaurio?
        final var authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(
                ValidateTokenDTO.builder()
                    .username( user.getName() )
                    .authorities( authorities )
                    .isAuthenticated( true )
                    .build()
        );
    }
    @Data
    @Builder
    public static class ValidateTokenDTO {
        private boolean isAuthenticated;
        private String username;
        private List<String> authorities;
    }

    // INICIAR SESION
    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authenticate( @Valid @RequestBody AuthRequestDTO request ) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( request.getEmail(), request.getPassword() );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = tokenProvider.createToken (authentication );
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add( JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt );
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/register")
//    @PreAuthorize( "hasAuthority( \"" + AuthorityConstant.ADMIN + "\" )" )
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO request ){
        final var newUser = this.userService.createUser( request );
        return new ResponseEntity<>( newUser, HttpStatus.CREATED );
    }

    static class JWTToken {
        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }

}
