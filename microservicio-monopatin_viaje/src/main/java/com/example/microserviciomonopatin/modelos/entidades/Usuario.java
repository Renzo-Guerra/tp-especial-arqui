package com.example.microserviciomonopatin.modelos.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    @ManyToMany(mappedBy = "usuarios")
    private List<Cuenta> cuentas;

    public Usuario() { }

    public Usuario(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fecha = LocalDateTime.now();
    }


}
