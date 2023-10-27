package com.example.microserviciomonopatin.modelos.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "viaje")
public class Viaje implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_cuenta;
    private Long id_usuario;
    private Long id_monopatin;
    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Double km_recorridos;
    private Long segundos_estacionado;

}
