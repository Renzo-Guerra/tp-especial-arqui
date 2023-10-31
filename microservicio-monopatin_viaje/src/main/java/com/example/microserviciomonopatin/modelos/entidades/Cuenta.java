package com.example.microserviciomonopatin.modelos.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Cuenta implements Serializable {
    private Long id;
    private Long id_mercado_pago; // muchas cuentas pueden tener la misma cuenta de mercado pago!
    private Double saldo;
    private Boolean isHabilitada;
    private LocalDateTime fecha_alta;

    public Cuenta() { }

    public Cuenta (Long id_mercado_pago){
        this.id_mercado_pago = id_mercado_pago;
        this.saldo = 0.0;
        this.isHabilitada = true;
        this.fecha_alta = LocalDateTime.now();
    }
}
