package com.example.microservicioparada.entidades;


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
}
