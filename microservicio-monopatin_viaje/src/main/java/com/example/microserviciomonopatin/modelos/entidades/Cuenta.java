package com.example.microserviciomonopatin.modelos.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@ToString
public class Cuenta implements Serializable {
    private Long id;
    private Long id_mercado_pago; // muchas cuentas pueden tener la misma cuenta de mercado pago!
    private Double saldo;
    private Boolean isHabilitada;
    private LocalDateTime fecha_alta;
    private List<Usuario> usuarios;

    public void agregarUsuario( Usuario u ) {
        u.setCuentas( List.of( this ) );
        if( this.usuarios == null ) this.usuarios = new ArrayList<>();
        this.usuarios.add(u);
    }

    public boolean contieneUsuario(Long id_usuario){
        Iterator<Usuario> it_usuarios = this.getUsuarios().iterator();

        while(it_usuarios.hasNext()){
            Usuario usuario_actual = it_usuarios.next();
            if(usuario_actual.getId().equals(id_usuario)){
                return true;
            }
        }

        return false;
    }
}
