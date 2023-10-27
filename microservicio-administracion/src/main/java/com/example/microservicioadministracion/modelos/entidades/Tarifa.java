package com.example.microservicioadministracion.modelos.entidades;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha_creacion;
    private Double tarifa;
    private Double porc_recargo;
    private LocalDateTime fecha_caducacion;
}
