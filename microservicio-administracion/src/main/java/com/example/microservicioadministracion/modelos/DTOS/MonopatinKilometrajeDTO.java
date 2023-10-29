package com.example.microservicioadministracion.modelos.DTOS;

import lombok.Data;

@Data
public class MonopatinKilometrajeDTO {
    private Long id_monopatin;
    private Double kilometros_recorridos;


    public MonopatinKilometrajeDTO() { }

    public MonopatinKilometrajeDTO(Long id_monopatin, Double kilometros_recorridos){
        this.id_monopatin = id_monopatin;
        this.kilometros_recorridos = kilometros_recorridos;
    }
}
