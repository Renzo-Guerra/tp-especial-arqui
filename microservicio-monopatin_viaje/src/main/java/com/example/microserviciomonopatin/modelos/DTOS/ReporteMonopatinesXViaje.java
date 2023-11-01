package com.example.microserviciomonopatin.modelos.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReporteMonopatinesXViaje {
    private Long id_monopatin;
    private Long cant_viajes;
}

