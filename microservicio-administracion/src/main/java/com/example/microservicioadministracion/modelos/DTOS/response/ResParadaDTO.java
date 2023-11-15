package com.example.microservicioadministracion.modelos.DTOS.response;

import com.example.microservicioparada.modelos.entidades.ParadaMongo;
import lombok.Data;

@Data
public class ResParadaDTO {
    private String id;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;

    public ResParadaDTO(String id, Double latitud, Double longitud, Boolean isHabilitada) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.isHabilitada = isHabilitada;
    }

    public ResParadaDTO(ParadaMongo parada){
        this.id = parada.getId_parada();
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
        this.isHabilitada = parada.getIsHabilitada();
    }
}
