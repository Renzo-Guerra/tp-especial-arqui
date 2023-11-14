package com.example.service;

import com.example.entity.AuthUser;
import com.example.repository.AuthorityRepository;
import com.example.repository.UserRepository;
import com.example.service.dto.user.request.UserRequestDTO;
import com.example.service.dto.user.response.UserResponseDTO;
import com.example.service.exception.user.EnumUserException;
import com.example.service.exception.user.NotFoundException;
import com.example.service.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserRequestDTO request ) {
        if( this.userRepository.existsUsersByEmailIgnoreCase( request.getEmail() ) )
            throw new UserException( EnumUserException.already_exist, String.format("Ya existe un usuario con email %s", request.getEmail() ) );
        // Valida que todas las autoridades del usuario existan en la entidad autoridades
        final var authorities = request.getAuthorities()
                .stream()
                .map( string -> this.authorityRepository.findByName( string ).orElseThrow( () -> new NotFoundException("Autority", string ) ) )
                .toList();
        // Valida que tenga almenos 1 autoridad
        if( authorities.isEmpty() )
            throw new UserException( EnumUserException.invalid_authorities,
                                        String.format("No se encontro ninguna autoridad con id %s", request.getAuthorities().toString() ) );
        final var user = new AuthUser( request );
        user.setAuthorities( authorities );
        final var encryptedPassword = passwordEncoder.encode( request.getPassword() );
        user.setPassword( encryptedPassword );
        final var createdUser = this.userRepository.save( user );
        return new UserResponseDTO( createdUser );
    }
}
