package com.example.microserviciousuario.modelos.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
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
    private Double tarifa;
    private Double porc_recargo;
    private Long segundos_estacionado;
    private Double costo_viaje;

    public Viaje() { }

    public Viaje(Long id_cuenta, Long id_usuario, Long id_monopatin, Double tarifa, Double porc_recargo) {
        this.id_cuenta = id_cuenta;
        this.id_usuario = id_usuario;
        this.id_monopatin = id_monopatin;
        this.inicio = LocalDateTime.now();
        this.fin = null;
        this.km_recorridos = 0.0;
        this.tarifa = tarifa;
        this.porc_recargo = porc_recargo;
        this.segundos_estacionado = new Long(0L);
        this.costo_viaje = 0.0;
    }
}
