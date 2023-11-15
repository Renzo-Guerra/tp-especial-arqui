package com.example.microservicioparada.modelos.DTOs.request;

import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReqParadaDTO {
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;

    public ReqParadaDTO(Double latitud, Double longitud, Boolean isHabilitada) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.isHabilitada = isHabilitada;
    }

    public ReqParadaDTO(ParadaMongo parada){
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
        this.isHabilitada = parada.getIsHabilitada();
    }
}
