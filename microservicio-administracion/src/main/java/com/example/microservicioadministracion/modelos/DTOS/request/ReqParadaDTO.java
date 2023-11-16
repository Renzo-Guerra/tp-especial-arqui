package com.example.microservicioadministracion.modelos.DTOS.request;

import com.example.microservicioadministracion.modelos.DTOS.ParadaMongoDTO;
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

    public ReqParadaDTO(ParadaMongoDTO parada){
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
        this.isHabilitada = parada.getIsHabilitada();
    }
}
