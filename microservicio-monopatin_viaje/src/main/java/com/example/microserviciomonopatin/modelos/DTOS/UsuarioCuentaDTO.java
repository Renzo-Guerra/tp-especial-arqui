package com.example.microserviciomonopatin.modelos.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioCuentaDTO {
    private Long id_cuenta;
    private Long id_usuario;
    private Double saldo_cuenta;
}
