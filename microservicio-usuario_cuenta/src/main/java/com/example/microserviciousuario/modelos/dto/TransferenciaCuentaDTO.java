package com.example.microserviciousuario.modelos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaCuentaDTO {
    private Double monto_anterior;
    private Double monto_nuevo;
    private Double diferencia;
}
