package com.example.microserviciousuario.modelos.DTOS;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ParadaMongoDTO {
    private String id_parada;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;

    public ParadaMongoDTO(Double latitud, Double longitud, Boolean isHabilitada) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.isHabilitada = isHabilitada;
    }
}
