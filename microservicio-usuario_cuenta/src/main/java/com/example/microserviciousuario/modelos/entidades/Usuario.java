package com.example.microserviciousuario.modelos.entidades;

import jakarta.persistence.*;
import lombok.Data;
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
    @Column(name = "fecha_alta")
    private LocalDateTime fecha;

    @ManyToMany(mappedBy = "usuarios", fetch = FetchType.LAZY)
    private List<Cuenta> cuentas;

    public Usuario() {
        this.cuentas = new ArrayList<>();
    }

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = LocalDateTime.now();
        this.cuentas = new ArrayList<>();
    }


}
