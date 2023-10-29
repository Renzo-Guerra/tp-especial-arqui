package com.example.microserviciousuario.modelos.DTOS;

import lombok.Data;

import java.io.Serializable;

@Data
public class UsuarioCreacionDTO implements Serializable {

    private String nombre;
    private String apellido;

}
