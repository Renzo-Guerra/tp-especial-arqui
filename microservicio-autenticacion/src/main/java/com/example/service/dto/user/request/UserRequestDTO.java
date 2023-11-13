package com.example.service.dto.user.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class UserRequestDTO {

    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private Set<Long> cuentas;
    private Set<String> authorities;

}
