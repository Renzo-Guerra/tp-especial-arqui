package com.example.microserviciousuario.modelos.DTOS.response;

import com.example.microserviciousuario.modelos.DTOS.ParadaMongoDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

    public ResParadaDTO(ParadaMongoDTO parada){
        this.id = parada.getId_parada();
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
        this.isHabilitada = parada.getIsHabilitada();
    }
}
