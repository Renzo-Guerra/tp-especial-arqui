package com.example.microservicioadministracion.repositorios;

import com.example.microservicioadministracion.modelos.entidades.Tarifa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRespositorio extends CrudRepository<Tarifa, Long> {

    @Query("""
            SELECT t 
            FROM Tarifa t 
            ORDER BY t.fecha_creacion DESC 
            LIMIT 1
            """)
    public Optional<Tarifa> buscarUltimaTarifa();
}
