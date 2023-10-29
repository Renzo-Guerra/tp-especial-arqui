package com.example.microserviciousuario.modelos.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCuentaDTO {

    private Long id_cuenta;
    private Long id_usuario;
}
