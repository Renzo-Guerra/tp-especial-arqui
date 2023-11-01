package com.example.microserviciomonopatin.modelos.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parada {
    private Long id_parada;
    private Double latitud;
    private Double longitud;
    private Boolean isHabilitada;
}
