package com.example.microservicioadministracion.modelos.entidades;

import lombok.Data;

@Data
public class TarifaCrearTarifaDTO {
    private Double tarifa;
    private Double porc_recargo;

    public TarifaCrearTarifaDTO(Double tarifa, Double porc_recargo){
        this.tarifa = tarifa;
        this.porc_recargo = porc_recargo;
    }
}
