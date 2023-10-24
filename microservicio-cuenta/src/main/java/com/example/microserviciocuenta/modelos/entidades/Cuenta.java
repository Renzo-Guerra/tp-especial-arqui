package com.example.microserviciocuenta.modelos.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "cuenta")
public class Cuenta  implements Serializable {
    @Id
    private Long id;

    private double saldo;

    private boolean isHabilitada;

    @Column(name = "fecha_alta")
    private LocalDateTime fecha_alta;

    @Column(name = "fecha_baja")
    private LocalDateTime fecha_baja;
}
