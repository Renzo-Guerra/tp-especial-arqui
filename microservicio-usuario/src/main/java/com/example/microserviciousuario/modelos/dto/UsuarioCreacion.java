package com.example.microserviciousuario.modelos.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioCreacion implements Serializable {

    private String nombre;
    private String apellido;

}
