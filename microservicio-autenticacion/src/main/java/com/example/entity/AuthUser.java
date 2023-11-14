package com.example.entity;

import com.example.service.dto.user.request.UserRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


@Data
@NoArgsConstructor
@Entity
public class AuthUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @jakarta.persistence.Id
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @Column( nullable = false )
    private String nombre;
    @Column( nullable = false )
    private String apellido;
    @Column( nullable = false )
    private String email;
    @Column( nullable = false )
    private String password;

    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
    @JoinTable(
            name = "rel_auth_user_authority",
            joinColumns = @JoinColumn(name = "auth_user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities;

    public AuthUser(UserRequestDTO request) {
        this.nombre = request.getNombre();
        this.apellido = request.getApellido();
        this.email = request.getEmail();
    }

    public void setAuthorities( Collection<Authority> authorities ){
        this.authorities = new HashSet<>( authorities );
    }

}

