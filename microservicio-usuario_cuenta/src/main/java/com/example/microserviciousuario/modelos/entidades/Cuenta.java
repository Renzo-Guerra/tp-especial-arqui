package com.example.microserviciousuario.modelos.entidades;

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

    @ManyToMany( fetch = FetchType.LAZY )
    @JoinTable(
            name = "rel_cuenta__usuario",
            joinColumns = @JoinColumn(name = "cuenta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    @JsonIgnoreProperties( value = "cuentas", allowSetters = true )
    private List<Usuario> usuarios;

    public Cuenta() {}

    public Cuenta (Long id_mercado_pago){
        this.id_mercado_pago = id_mercado_pago;
        this.saldo = 0.0;
        this.isHabilitada = true;
        this.fecha_alta = LocalDateTime.now();
        this.usuarios = new ArrayList<>();
    }

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
