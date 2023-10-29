package com.example.microserviciomonopatin.modelos.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinTiempoFuncionamiento {
    private Long id_monopatin;
    private Long tiempo_total;
    private Long tiempo_total_funcionando;
    private Long tiempo_total_pausado;
}
