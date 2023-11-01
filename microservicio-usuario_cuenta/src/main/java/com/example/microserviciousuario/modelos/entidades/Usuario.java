package com.example.microserviciousuario.modelos.entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fecha_alta;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    @JsonIgnoreProperties( value = "usuarios", allowSetters = true )
    private List<Cuenta> cuentas;

    public Usuario() {}

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha_alta = LocalDateTime.now();
        this.cuentas = new ArrayList<>();
    }

    public void agregarCuenta( Cuenta c ) {
        c.setUsuarios( List.of( this ) );
        if( this.cuentas == null ) this.cuentas = new ArrayList<>();
        this.cuentas.add(c);
    }


}
