package com.example.microserviciomonopatin.modelos.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fecha_creacion;
    private Double tarifa;
    private Double porc_recargo;
    private LocalDateTime fecha_caducacion;

    public Tarifa() {
    }

    public Tarifa(Double tarifa, Double porc_recargo) {
        this.fecha_creacion = LocalDateTime.now();
        this.tarifa = tarifa;
        this.porc_recargo = porc_recargo;
        this.fecha_caducacion = null;
    }
}
