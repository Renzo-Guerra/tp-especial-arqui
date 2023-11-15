package com.example.microservicioparada.modelos.entidades;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ParadaMongo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id_parada;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;
}
