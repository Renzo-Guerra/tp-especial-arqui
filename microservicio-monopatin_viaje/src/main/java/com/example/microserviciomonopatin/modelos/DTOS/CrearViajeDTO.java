package com.example.microserviciomonopatin.modelos.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class CrearViajeDTO {
    private Long id_cuenta;
    private Long id_usuario;
    private Long id_monopatin;
}
