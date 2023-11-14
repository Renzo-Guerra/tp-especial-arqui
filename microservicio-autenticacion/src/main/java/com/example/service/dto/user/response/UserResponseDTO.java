package com.example.service.dto.user.response;

import com.example.entity.AuthUser;
import lombok.Data;

@Data
public class UserResponseDTO {

    private final long id;
    private final String nombre;
    private final String apellido;
    private final String email;

    public UserResponseDTO( AuthUser authUser){
        this.id = authUser.getId();
        this.nombre = authUser.getNombre();
        this.apellido = authUser.getApellido();
        this.email = authUser.getEmail();
    }

}
