package com.example.microservicioparada.modelos.entidades;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class ParadaMongo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id_parada;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;

    public ParadaMongo(Double latitud, Double longitud, Boolean isHabilitada) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.isHabilitada = isHabilitada;
    }
}
