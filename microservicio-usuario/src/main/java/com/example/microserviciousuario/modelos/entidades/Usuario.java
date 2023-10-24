package com.example.microserviciousuario.modelos.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    private Long id;

    private String nombre;

    private String apellido;

    @Column(name = "fecha_alta")
    private LocalDateTime fecha;
}
