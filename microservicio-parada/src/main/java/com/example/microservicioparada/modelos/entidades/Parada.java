package com.example.microservicioparada.modelos.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "parada")
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_parada;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;

    public Parada(Double latitud, Double longitud, Boolean isHabilitada) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.isHabilitada = isHabilitada;
    }
}
