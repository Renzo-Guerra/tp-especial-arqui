package com.example.microservicioadministracion.modelos.entidades;

import lombok.Data;

@Data
public class MonopatinCambiarEstadoDTO {
    private Long id;
    private String estado;

    public MonopatinCambiarEstadoDTO(Long id, String estado){
        this.id = id;
        this.estado = estado;
    }
}
