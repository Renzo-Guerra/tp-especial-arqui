package com.example.microservicioadministracion.modelos.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteFacturacionDTO {
    private Integer mesDesde;
    private Integer mesHasta;
    private Integer anio;
    private Double total_facturacion;
}
