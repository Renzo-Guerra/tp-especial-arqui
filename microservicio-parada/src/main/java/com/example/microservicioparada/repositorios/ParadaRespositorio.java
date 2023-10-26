package com.example.microservicioparada.repositorios;

import com.example.microservicioparada.entidades.Parada;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParadaRespositorio extends CrudRepository<Parada, Long> {

    @Query("""
            SELECT  p 
            FROM Parada p 
            WHERE p.latitud=:latitud AND p.longitud=:longitud 
            ORDER BY p.id_parada 
            LIMIT 1
            """)
    Optional<Parada> traerPorCoordenadas(Double latitud, Double longitud);
}
