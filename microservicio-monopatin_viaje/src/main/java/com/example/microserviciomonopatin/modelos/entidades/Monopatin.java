package com.example.microserviciomonopatin.modelos.entidades;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "monopatin")
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_monopatin;
    private Long gps;
    private Double latitud;
    private Double longitud;
    private String estado;
}
