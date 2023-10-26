package com.example.microserviciomonopatin.modelos.entidades;

import lombok.Data;

@Data
public class MonopatinCambiarEstadoDTO {
    private Long id;
    private String estado;

    public MonopatinCambiarEstadoDTO(Long idMonopatin, String estado) {
        this.id = idMonopatin;
        this.estado = estado;
    }
}
