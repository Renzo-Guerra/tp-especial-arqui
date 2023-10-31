package com.example.microserviciousuario.modelos.entidades;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
@Table(name = "cuenta")
public class Cuenta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long id_mercado_pago; // muchas cuentas pueden tener la misma cuenta de mercado pago!
    private Double saldo;
    private Boolean isHabilitada;
    @Column(name = "fecha_alta")
    private LocalDateTime fecha_alta;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    public Cuenta() {
        this.usuarios = new ArrayList<>();
    }

    public Cuenta (Long id_mercado_pago){
        this.id_mercado_pago = id_mercado_pago;
        this.saldo = 0.0;
        this.isHabilitada = true;
        this.fecha_alta = LocalDateTime.now();
        this.usuarios = new ArrayList<>();
    }

    public void agregarUsuario( Usuario u ) {
        u.setCuentas( List.of( this ) );
        this.usuarios.add(u);
    }
}
