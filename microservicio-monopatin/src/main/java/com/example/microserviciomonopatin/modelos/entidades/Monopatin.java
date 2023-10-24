package com.example.microserviciomonopatin.modelos.entidades;


import com.example.microserviciomonopatin.repositorios.MonopatinRespositorio;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name = "monopatin")
public class Monopatin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_monopatin;
    private Long gps;
    private Double latitud;
    private Double longitud;

}
