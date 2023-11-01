package com.example.microserviciomonopatin.modelos.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
public class Usuario implements Serializable {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fecha_alta;
    private List<Cuenta> cuentas;
}
