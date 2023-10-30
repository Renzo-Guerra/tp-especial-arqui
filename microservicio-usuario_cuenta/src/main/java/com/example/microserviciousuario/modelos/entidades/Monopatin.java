package com.example.microserviciousuario.modelos.entidades;


import jakarta.persistence.*;
import lombok.Data;


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
