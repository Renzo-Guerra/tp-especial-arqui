package com.example.microservicioadministracion.modelos.entidades;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "parada")
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_parada;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;
}
