package com.example.microserviciomonopatin.repositorios;

import com.example.microserviciomonopatin.modelos.DTOS.ReporteMonopatinesXViaje;
import com.example.microserviciomonopatin.modelos.DTOS.ReporteFacturacionDTO;

import com.example.microserviciomonopatin.modelos.entidades.Viaje;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepositorio extends CrudRepository<Viaje, Long> {

    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.ReporteMonopatinesXViaje(v.id_monopatin, COUNT(v.fin)) 
            FROM Viaje v 
            WHERE EXTRACT(YEAR FROM v.fin) = :anio
            GROUP BY v.id_monopatin
            HAVING count(v.fin) > :cantidad
            """)
    List<ReporteMonopatinesXViaje> cantidadViajesMayorAXAño(Integer cantidad, Integer anio);
    @Query("""
            SELECT new com.example.microserviciomonopatin.modelos.DTOS.ReporteFacturacionDTO(:mes1, :mes2, :anio, sum(v.costo_total_viaje))
            FROM Viaje v  
            WHERE (EXTRACT(month from v.fin) between :mes1 and :mes2) and EXTRACT(YEAR from v.fin) = :anio  
            """)
    ReporteFacturacionDTO facturacionViajesRangoMesesPorAnio(Integer mes1, Integer mes2, Integer anio);
}
