package com.example.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @jakarta.persistence.Id
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    private Double saldo;

    @ManyToMany( fetch = FetchType.LAZY, mappedBy = "cuentas" )
    private Set<User> usuarios;
}
